package com.craftinginterpreters.lox;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class LoxTest {
    static class TestInterpreter extends Interpreter {
        List<String> actual = new ArrayList<>();
        List<String> expected;

        TestInterpreter(List<String> expected) {
            super();
            this.expected = expected;
        }

        @Override
        void interpret(List<Stmt> statements) {
            try {
                for (Stmt statement : statements) {
                    this.execute(statement);
                }
            } catch (RuntimeError error) {
                Lox.runtimeError(error);
            }

            this.check();
        }

        @Override
        public void visitPrintStmt(Stmt.Print stmt) {
            Object value = this.evaluate(stmt.expression);
            this.actual.add(this.stringify(value));
        }

        public void check() {
            for (int i = 0; i < this.expected.size(); ++i) {
                Assertions.assertEquals(this.expected.get(i), this.actual.get(i));
            }
        }
    }

    public static void run(String source, TestInterpreter interpreter) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        Parser parser = new Parser(tokens);

        List<Stmt> statements = parser.parse();
        Resolver resolver = new Resolver(interpreter);
        resolver.resolve(statements);

        interpreter.interpret(statements);
    }

    @Nested
    @DisplayName("Assignment Tests")
    class Assignment {
        @Test
        @DisplayName("Test Associativity")
        void test_associativity() {
            final String input = """
                    var a = "a";
                    var b = "b";
                    var c = "c";
                    
                    a = b = c;
                    print a;
                    print b;
                    print c;""";

            LoxTest.run(input, new TestInterpreter(List.of("c", "c", "c")));
        }

        @Test
        @DisplayName("Test Global")
        void test_global() {
            final String input = """
                    var a = "before";
                    print a;
                    
                    a = "after";
                    print a;
                    
                    a = "arg";
                    print a;""";

            LoxTest.run(input, new TestInterpreter(List.of("before", "after", "arg")));
        }

        @Test
        @DisplayName("Test Local")
        void test_grouping() {
            final String input = """
                    {
                        var a = "before";
                        print a;
                    
                        a = "after";
                        print a;
                    
                        a = "arg";
                        print a;
                    }""";

            LoxTest.run(input, new TestInterpreter(List.of("before", "after", "arg")));
        }

        @Test
        @DisplayName("Test Syntax")
        void test_syntax() {
            final String input = """
                    var a = "before";
                    var c = a = "var";
                    print a;
                    print c;""";

            LoxTest.run(input, new TestInterpreter(List.of("var", "var")));
        }
    }

    @Nested
    @DisplayName("Block Tests")
    class Block {
        @Test
        @DisplayName("Test Empty")
        void test_empty() {
            final String input = """
                    {}
                    if (true) {}
                    if (false) {} else {}
                    
                    print "ok";""";

            LoxTest.run(input, new TestInterpreter(List.of("ok")));
        }

        @Test
        @DisplayName("Test Scope")
        void test_scope() {
            final String input = """
                    var a = "outer";
                    {
                        var a = "inner";
                        print a;
                    }
                    
                    print a;""";

            LoxTest.run(input, new TestInterpreter(List.of("inner", "outer")));
        }
    }

    @Nested
    @DisplayName("Bool Tests")
    class Bool {
        @Test
        @DisplayName("Test Equality")
        void test_equality() {
            final String input = """
                    print true == true;
                    print true == false;
                    print false == true;
                    print false == false;
                    
                    print true == 1;
                    print false == 0;
                    print true == "true";
                    print false == "false";
                    print false == "";
                    
                    print true != true;
                    print true != false;
                    print false != true;
                    print false != false;
                    
                    print true != 1;
                    print false != 0;
                    print true != "true";
                    print false != "false";
                    print false != "";""";

            LoxTest.run(input, new TestInterpreter(List.of("true", "false", "false", "true", "false", "false", "false", "false", "false", "false", "true", "true", "false", "true", "true", "true", "true", "true")));
        }

        @Test
        @DisplayName("Test Not")
        void test_not() {
            final String input = """
                    print !true;
                    print !false;
                    print !!true;""";

            LoxTest.run(input, new TestInterpreter(List.of("false", "true", "true")));
        }
    }

    @Nested
    @DisplayName("Class Tests")
    class Class {
        @Test
        @DisplayName("Test Empty")
        void test_empty() {
            final String input = """
                    class Foo {}
                    print Foo;""";

            LoxTest.run(input, new TestInterpreter(List.of("Foo")));
        }

        @Test
        @DisplayName("Test Inherited Method")
        void test_inherited_method() {
            final String input = """
                    class Foo {
                        inFoo() {
                            print "in foo";
                        }
                    }
                    
                    class Bar < Foo {
                        inBar() {
                            print "in bar";
                        }
                    }
                    
                    class Baz < Bar {
                        inBaz() {
                            print "in baz";
                        }
                    }
                    
                    var baz = Baz();
                    baz.inFoo();
                    baz.inBar();
                    baz.inBaz();""";

            LoxTest.run(input, new TestInterpreter(List.of("in foo", "in bar", "in baz")));
        }

        @Test
        @DisplayName("Test Local Inherit Other")
        void test_local_inherit_other() {
            final String input = """
                    class A {}
                    
                    fun f() {
                        class B < A {}
                        return B;
                    }
                    
                    print f();""";

            LoxTest.run(input, new TestInterpreter(List.of("B")));
        }

        @Test
        @DisplayName("Test Local Reference Self")
        void test_local_reference_self() {
            final String input = """
                    {
                        class Foo {
                            returnSelf() {
                                return Foo;
                            }
                        }
                        
                        print Foo().returnSelf();
                    }""";

            LoxTest.run(input, new TestInterpreter(List.of("Foo")));
        }

        @Test
        @DisplayName("Test Reference Self")
        void test_reference_self() {
            final String input = """
                    class Foo {
                        returnSelf() {
                            return Foo;
                        }
                    }
                    
                    print Foo().returnSelf();""";

            LoxTest.run(input, new TestInterpreter(List.of("Foo")));
        }
    }

    @Nested
    @DisplayName("Closure Tests")
    class Closure {
        @Test
        @DisplayName("Test Assign To Closure")
        void test_assign_to_closure() {
            final String input = """
                    var f;
                    var g;
                    {
                        var local = "local";
                        fun f_() {
                            print local;
                            local = "after f";
                            print local;
                        }
                        f = f_;
                        
                        fun g_() {
                            print local;
                            local = "after g";
                            print local;
                        }
                        g = g_;
                    }
                    
                    f();
                    
                    g();""";

            LoxTest.run(input, new TestInterpreter(List.of("local", "after f", "after f", "after g")));
        }

        @Test
        @DisplayName("Test Assign To Shadowed Later")
        void test_assign_to_shadowed_later() {
            final String input = """
                    var a = "global";
                    
                    {
                        fun assign() {
                            a = "assigned";
                        }
                        
                        var a = "inner";
                        assign();
                        print a;
                    }
                    
                    print a;""";

            LoxTest.run(input, new TestInterpreter(List.of("inner", "assigned")));
        }

        @Test
        @DisplayName("Test Close Over Function Parameters")
        void test_close_over_function_parameter() {
            final String input = """
                    var f;
                    
                    fun foo(param) {
                        fun f_() {
                            print param;
                        }
                        f = f_;
                    }
                    foo("param");
                    
                    f();""";

            LoxTest.run(input, new TestInterpreter(List.of("param")));
        }

        @Test
        @DisplayName("Test Close Over Later Variable")
        void test_close_over_later_variable() {
            final String input = """
                    fun f() {
                        var a = "a";
                        var b = "b";
                        fun g() {
                            print b;
                            print a;
                        }
                        g();
                    }
                    f();""";

            LoxTest.run(input, new TestInterpreter(List.of("b", "a")));
        }

        @Test
        @DisplayName("Test Close Over Method Parameter")
        void test_close_over_method_parameter() {
            final String input = """
                    var f;
                    
                    class Foo {
                        method(param) {
                            fun f_() {
                                print param;
                            }
                            f = f_;
                        }
                    }
                    
                    Foo().method("param");
                    f();""";

            LoxTest.run(input, new TestInterpreter(List.of("param")));
        }

        @Test
        @DisplayName("Test Closed Closure In Function")
        void test_closed_closure_in_function() {
            final String input = """
                    var f;
                    
                    {
                        var local = "local";
                        fun f_() {
                            print local;
                        }
                        f = f_;
                    }
                    
                    f();""";

            LoxTest.run(input, new TestInterpreter(List.of("local")));
        }

        @Test
        @DisplayName("Test Nested Closure")
        void test_nested_closure() {
            final String input = """
                    var f;
                    
                    fun f1() {
                        var a = "a";
                        fun f2() {
                            var b = "b";
                            fun f3() {
                                var c = "c";
                                fun f4() {
                                    print a;
                                    print b;
                                    print c;
                                }
                                f = f4;
                            }
                            f3();
                        }
                        f2();
                    }
                    f1();
                    
                    f();""";

            LoxTest.run(input, new TestInterpreter(List.of("a", "b", "c")));
        }

        @Test
        @DisplayName("Test Open Closure In Function")
        void test_open_closure_in_function() {
            final String input = """
                    {
                        var local = "local";
                        fun f() {
                            print local;
                        }
                        f();
                    }""";

            LoxTest.run(input, new TestInterpreter(List.of("local")));
        }

        @Test
        @DisplayName("Test Reference Closure Multiple Times")
        void test_reference_closure_multiple_times() {
            final String input = """
                    var f;
                    
                    {
                        var a = "a";
                        fun f_() {
                            print a;
                            print a;
                        }
                        f = f_;
                    }
                    
                    f();""";

            LoxTest.run(input, new TestInterpreter(List.of("a", "a")));
        }

        @Test
        @DisplayName("Test Reuse Closure Slot")
        void test_reuse_closure_slot() {
            final String input = """
                    {
                        var f;
                    
                        {
                            var a = "a";
                            fun f_() { print a; }
                            f = f_;
                        }
                        
                        {
                            var b = "b";
                            f();
                        }
                    }""";

            LoxTest.run(input, new TestInterpreter(List.of("a")));
        }

        @Test
        @DisplayName("Test Shadow Closure With Local")
        void test_shadow_closure_with_local() {
            final String input = """
                    {
                        var foo = "closure";
                        fun f() {
                            {
                                print foo;
                                var foo = "shadow";
                                print foo;
                            }
                            print foo;
                        }
                        f();
                    }""";

            LoxTest.run(input, new TestInterpreter(List.of("closure", "shadow", "closure")));
        }

        @Test
        @DisplayName("Test Unused Closure")
        void test_unused_closure() {
            final String input = """
                    {
                        var a = "a";
                        if (false) {
                            fun foo() { a; }
                        }
                    }
                    print "ok";""";

            LoxTest.run(input, new TestInterpreter(List.of("ok")));
        }

        @Test
        @DisplayName("Test Unused Later Closure")
        void test_unused_later_closure() {
            final String input = """
                    var closure;
                    
                    {
                        var a = "a";
                        {
                            var b = "b";
                            fun returnA() {
                                return a;
                            }
                            
                            closure = returnA;
                            
                            if (false) {
                                fun returnB() {
                                    return b;
                                }
                            }
                        }
                        
                        print closure();
                    }""";

            LoxTest.run(input, new TestInterpreter(List.of("a")));
        }
    }

    @Test
    @DisplayName("Test Precedence")
    void test_precedence() {
        final String input = """
                print 2 + 3 * 4;
                print 20 - 3 * 4;
                print 2 + 6 / 3;
                print 2 - 6 / 3;
                
                print false == 2 < 1;
                print false == 1 > 2;
                print false == 2 <= 1;
                print false == 1 >= 2;
                
                print 1 - 1;
                print 1 -1;
                print 1- 1;
                print 1-1;
                
                print (2 * (6 - (2 + 2)));""";

        LoxTest.run(input, new TestInterpreter(List.of("14", "8", "4", "0", "true", "true", "true", "true", "0", "0", "0", "0", "4")));
    }
}