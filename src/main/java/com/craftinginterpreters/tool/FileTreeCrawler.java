package com.craftinginterpreters.tool;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;

public class FileTreeCrawler {
    private FileTreeCrawler() {
        super();
    }

    public static List<Path> crawl(Path root, Predicate<File> match) {
        List<Path> matches = new ArrayList<>();

        File current = root.toFile();
        if (current.isDirectory()) {
            Queue<File> queue = new ArrayDeque<>();
            queue.add(current);

            do {
                current = queue.poll();
                if (current == null)
                    continue;

                if (current.isDirectory()) {
                    File[] files = current.listFiles();
                    if (files != null) {
                        Collections.addAll(queue, files);
                    }
                } else {
                    if (match.test(current)) {
                        matches.add(current.toPath());
                    }
                }
            } while (!queue.isEmpty());

            return matches;
        }

        if (match.test(current)) {
            matches.add(current.toPath());
        }
        return matches;
    }
}