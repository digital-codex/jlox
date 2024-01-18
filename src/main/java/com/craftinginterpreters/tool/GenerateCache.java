package com.craftinginterpreters.tool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateCache {
    public static void main(String[] args) throws IOException {
        String outputDir = null;
        List<String> searchDirs = new ArrayList<>();
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equals("-o")) {
                outputDir = args[++i];
            } else {
                searchDirs.add(args[i]);
            }
        }

        if (outputDir == null) {
            System.err.println(
                    "Usage generate_cache <-o output directory> [search directory...]"
            );
            System.exit(64);
        }

        List<Path> paths = new ArrayList<>();
        for (String dir : searchDirs) {
            paths.addAll(GenerateCache.findScripts(dir));
        }

        GenerateCache.indexScripts(outputDir, paths);
    }

    public static List<Path> findScripts(String directory) {
        return FileTreeCrawler.crawl(
                Paths.get(directory),
                (File f) -> f.getName().split("\\.")[1].equals("lox")
        );
    }

    public static void indexScripts(String outputDir, List<Path> scripts) throws IOException {
        String file = outputDir + "/ScriptType.java";
        PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
        String pkg = outputDir.split("src/main/java/")[1]
                .replace("/", ".");
        writer.println("package " + pkg + ";");
        writer.println();
        writer.println("import java.util.HashMap;");
        writer.println("import java.util.Map;");
        writer.println();
        writer.println("enum ScriptType {");

        Map<String, Integer> counter = new HashMap<>();
        Map<Path, String> names = new HashMap<>();
        for (int i = 0; i < scripts.size(); ++i) {
            String name = scripts.get(i).getFileName().toString()
                    .split("\\.")[0].trim().toUpperCase();
            if (Character.isDigit(name.charAt(0))) {
                name = "$" + name;
            }
            String terminator = (i == scripts.size() - 1) ? ";" : ",";
            if (counter.containsKey(name)) {
                int index = counter.get(name);
                counter.put(name, ++index);
                name = name + "_" + index;
                writer.println("    " + name + terminator);
            } else {
                counter.put(name, 1);
                name = name + "_1";
                writer.println("    " + name + terminator);
            }
            names.put(scripts.get(i), name);
        }
        writer.println();

        writer.println(
                "    private static final Map<ScriptType, String> lookup = new HashMap<>();"
        );
        writer.println("    static {");
        for (Path script : scripts) {
            writer.println(
                    "        lookup.put( " + names.get(script) + ", \""
                            + script + "\" );"
            );
        }
        writer.println("    }");
        writer.println();

        writer.println("    public static String lookup(ScriptType type) {");
        writer.println("        return ScriptType.lookup.get(type);");
        writer.println("    }");
        writer.println("}");
        writer.close();
    }
}