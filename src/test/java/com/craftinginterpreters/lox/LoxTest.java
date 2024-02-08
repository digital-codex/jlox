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

    @Nested
    @DisplayName("Constructor Tests")
    class Constructor {
        @Test
        @DisplayName("Test Arguments")
        void test_arguments() {
            final String input = """
                    class Foo {
                        init(a, b) {
                            print "init";
                            this.a = a;
                            this.b = b;
                        }
                    }
                    
                    var foo = Foo(1, 2);
                    print foo.a;
                    print foo.b;""";

            LoxTest.run(input, new TestInterpreter(List.of("init", "1", "2")));
        }

        @Test
        @DisplayName("Test Call Init Early Return")
        void test_call_init_early_return() {
            final String input = """
                    class Foo {
                        init() {
                            print "init";
                            return;
                            print "nope";
                        }
                    }
                    
                    var foo = Foo();
                    print foo.init();""";

            LoxTest.run(input, new TestInterpreter(List.of("init", "init", "Foo instance")));
        }

        @Test
        @DisplayName("Test Call Init Explicitly")
        void test_call_init_explicitly() {
            final String input = """
                    class Foo {
                        init(arg) {
                            print "Foo.init(" + arg + ")";
                            this.field = "init";
                        }
                    }
                    
                    var foo = Foo("one");
                    foo.field = "field";
                    
                    var foo2 = foo.init("two");
                    print foo2;
                    
                    print foo.field;""";

            LoxTest.run(input, new TestInterpreter(List.of("Foo.init(one)", "Foo.init(two)", "Foo instance", "init")));
        }

        @Test
        @DisplayName("Test Default")
        void test_default() {
            final String input = """
                    class Foo {}
                    
                    var foo = Foo();
                    print foo;""";

            LoxTest.run(input, new TestInterpreter(List.of("Foo instance")));
        }

        @Test
        @DisplayName("Test Early Return")
        void test_early_return() {
            final String input = """
                    class Foo {
                        init() {
                            print "init";
                            return;
                            print "nope";
                        }
                    }
                    
                    var foo = Foo();
                    print foo;""";

            LoxTest.run(input, new TestInterpreter(List.of("init", "Foo instance")));
        }

        @Test
        @DisplayName("Test Init Not Method")
        void test_init_not_method() {
            final String input = """
                    class Foo {
                        init(arg) {
                            print "Foo.init(" + arg + ")";
                            this.field = "init";
                        }
                    }
                    
                    fun init() {
                        print "not initializer";
                    }
                    
                    init();""";

            LoxTest.run(input, new TestInterpreter(List.of("not initializer")));
        }

        @Test
        @DisplayName("Test Return In Nested Function")
        void test_return_in_nested_function() {
            final String input = """
                    class Foo {
                        init() {
                            fun init() {
                                return "bar";
                            }
                            print init();
                        }
                    }
                    
                    print Foo();""";

            LoxTest.run(input, new TestInterpreter(List.of("bar", "Foo instance")));
        }
    }

    @Nested
    @DisplayName("Field Tests")
    class Field {
        @Test
        @DisplayName("Test Call Function field")
        void test_call_function_field() {
            final String input = """
                    class Foo {}
                    
                    fun bar(a, b) {
                        print "bar";
                        print a;
                        print b;
                    }
                    
                    var foo = Foo();
                    foo.bar = bar;
                    
                    foo.bar(1, 2);""";

            LoxTest.run(input, new TestInterpreter(List.of("bar", "1", "2")));
        }

        @Test
        @DisplayName("Test Get and Set Method")
        void test_get_and_set_method() {
            final String input = """
                    class Foo {
                        method(a) {
                            print "method";
                            print a;
                        }
                        other(a) {
                            print "other";
                            print a;
                        }
                    }
                    
                    var foo = Foo();
                    var method = foo.method;
                    
                    foo.method = foo.other;
                    foo.method(1);
                    
                    method(2);""";

            LoxTest.run(input, new TestInterpreter(List.of("other", "1", "method", "2")));
        }

        @Test
        @DisplayName("Test Many")
        void test_many() {
            final String input = """
                    class Foo {}

                    var foo = Foo();
                    fun setFields() {
                        foo.bilberry = "bilberry";
                        foo.lime = "lime";
                        foo.elderberry = "elderberry";
                        foo.raspberry = "raspberry";
                        foo.gooseberry = "gooseberry";
                        foo.longan = "longan";
                        foo.mandarine = "mandarine";
                        foo.kiwifruit = "kiwifruit";
                        foo.orange = "orange";
                        foo.pomegranate = "pomegranate";
                        foo.tomato = "tomato";
                        foo.banana = "banana";
                        foo.juniper = "juniper";
                        foo.damson = "damson";
                        foo.blackcurrant = "blackcurrant";
                        foo.peach = "peach";
                        foo.grape = "grape";
                        foo.mango = "mango";
                        foo.redcurrant = "redcurrant";
                        foo.watermelon = "watermelon";
                        foo.plumcot = "plumcot";
                        foo.papaya = "papaya";
                        foo.cloudberry = "cloudberry";
                        foo.rambutan = "rambutan";
                        foo.salak = "salak";
                        foo.physalis = "physalis";
                        foo.huckleberry = "huckleberry";
                        foo.coconut = "coconut";
                        foo.date = "date";
                        foo.tamarind = "tamarind";
                        foo.lychee = "lychee";
                        foo.raisin = "raisin";
                        foo.apple = "apple";
                        foo.avocado = "avocado";
                        foo.nectarine = "nectarine";
                        foo.pomelo = "pomelo";
                        foo.melon = "melon";
                        foo.currant = "currant";
                        foo.plum = "plum";
                        foo.persimmon = "persimmon";
                        foo.olive = "olive";
                        foo.cranberry = "cranberry";
                        foo.boysenberry = "boysenberry";
                        foo.blackberry = "blackberry";
                        foo.passionfruit = "passionfruit";
                        foo.mulberry = "mulberry";
                        foo.marionberry = "marionberry";
                        foo.plantain = "plantain";
                        foo.lemon = "lemon";
                        foo.yuzu = "yuzu";
                        foo.loquat = "loquat";
                        foo.kumquat = "kumquat";
                        foo.salmonberry = "salmonberry";
                        foo.tangerine = "tangerine";
                        foo.durian = "durian";
                        foo.pear = "pear";
                        foo.cantaloupe = "cantaloupe";
                        foo.quince = "quince";
                        foo.guava = "guava";
                        foo.strawberry = "strawberry";
                        foo.nance = "nance";
                        foo.apricot = "apricot";
                        foo.jambul = "jambul";
                        foo.grapefruit = "grapefruit";
                        foo.clementine = "clementine";
                        foo.jujube = "jujube";
                        foo.cherry = "cherry";
                        foo.feijoa = "feijoa";
                        foo.jackfruit = "jackfruit";
                        foo.fig = "fig";
                        foo.cherimoya = "cherimoya";
                        foo.pineapple = "pineapple";
                        foo.blueberry = "blueberry";
                        foo.jabuticaba = "jabuticaba";
                        foo.miracle = "miracle";
                        foo.dragonfruit = "dragonfruit";
                        foo.satsuma = "satsuma";
                        foo.tamarillo = "tamarillo";
                        foo.honeydew = "honeydew";
                    }

                    setFields();

                    fun printFields() {
                        print foo.apple; // expect: apple
                        print foo.apricot; // expect: apricot
                        print foo.avocado; // expect: avocado
                        print foo.banana; // expect: banana
                        print foo.bilberry; // expect: bilberry
                        print foo.blackberry; // expect: blackberry
                        print foo.blackcurrant; // expect: blackcurrant
                        print foo.blueberry; // expect: blueberry
                        print foo.boysenberry; // expect: boysenberry
                        print foo.cantaloupe; // expect: cantaloupe
                        print foo.cherimoya; // expect: cherimoya
                        print foo.cherry; // expect: cherry
                        print foo.clementine; // expect: clementine
                        print foo.cloudberry; // expect: cloudberry
                        print foo.coconut; // expect: coconut
                        print foo.cranberry; // expect: cranberry
                        print foo.currant; // expect: currant
                        print foo.damson; // expect: damson
                        print foo.date; // expect: date
                        print foo.dragonfruit; // expect: dragonfruit
                        print foo.durian; // expect: durian
                        print foo.elderberry; // expect: elderberry
                        print foo.feijoa; // expect: feijoa
                        print foo.fig; // expect: fig
                        print foo.gooseberry; // expect: gooseberry
                        print foo.grape; // expect: grape
                        print foo.grapefruit; // expect: grapefruit
                        print foo.guava; // expect: guava
                        print foo.honeydew; // expect: honeydew
                        print foo.huckleberry; // expect: huckleberry
                        print foo.jabuticaba; // expect: jabuticaba
                        print foo.jackfruit; // expect: jackfruit
                        print foo.jambul; // expect: jambul
                        print foo.jujube; // expect: jujube
                        print foo.juniper; // expect: juniper
                        print foo.kiwifruit; // expect: kiwifruit
                        print foo.kumquat; // expect: kumquat
                        print foo.lemon; // expect: lemon
                        print foo.lime; // expect: lime
                        print foo.longan; // expect: longan
                        print foo.loquat; // expect: loquat
                        print foo.lychee; // expect: lychee
                        print foo.mandarine; // expect: mandarine
                        print foo.mango; // expect: mango
                        print foo.marionberry; // expect: marionberry
                        print foo.melon; // expect: melon
                        print foo.miracle; // expect: miracle
                        print foo.mulberry; // expect: mulberry
                        print foo.nance; // expect: nance
                        print foo.nectarine; // expect: nectarine
                        print foo.olive; // expect: olive
                        print foo.orange; // expect: orange
                        print foo.papaya; // expect: papaya
                        print foo.passionfruit; // expect: passionfruit
                        print foo.peach; // expect: peach
                        print foo.pear; // expect: pear
                        print foo.persimmon; // expect: persimmon
                        print foo.physalis; // expect: physalis
                        print foo.pineapple; // expect: pineapple
                        print foo.plantain; // expect: plantain
                        print foo.plum; // expect: plum
                        print foo.plumcot; // expect: plumcot
                        print foo.pomegranate; // expect: pomegranate
                        print foo.pomelo; // expect: pomelo
                        print foo.quince; // expect: quince
                        print foo.raisin; // expect: raisin
                        print foo.rambutan; // expect: rambutan
                        print foo.raspberry; // expect: raspberry
                        print foo.redcurrant; // expect: redcurrant
                        print foo.salak; // expect: salak
                        print foo.salmonberry; // expect: salmonberry
                        print foo.satsuma; // expect: satsuma
                        print foo.strawberry; // expect: strawberry
                        print foo.tamarillo; // expect: tamarillo
                        print foo.tamarind; // expect: tamarind
                        print foo.tangerine; // expect: tangerine
                        print foo.tomato; // expect: tomato
                        print foo.watermelon; // expect: watermelon
                        print foo.yuzu; // expect: yuzu
                    }

                    printFields();""";

            LoxTest.run(input, new TestInterpreter(List.of("apple", "apricot", "avocado", "banana", "bilberry", "blackberry", "blackcurrant", "blueberry", "boysenberry", "cantaloupe", "cherimoya", "cherry", "clementine", "cloudberry", "coconut", "cranberry", "currant", "damson", "date", "dragonfruit", "durian", "elderberry", "feijoa", "fig", "gooseberry", "grape", "grapefruit", "guava", "honeydew", "huckleberry", "jabuticaba", "jackfruit", "jambul", "jujube", "juniper", "kiwifruit", "kumquat", "lemon", "lime", "longan", "loquat", "lychee", "mandarine", "mango", "marionberry", "melon", "miracle", "mulberry", "nance", "nectarine", "olive", "orange", "papaya", "passionfruit", "peach", "pear", "persimmon", "physalis", "pineapple", "plantain", "plum", "plumcot", "pomegranate", "pomelo", "quince", "raisin", "rambutan", "raspberry", "redcurrant", "salak", "salmonberry", "satsuma", "strawberry", "tamarillo", "tamarind", "tangerine", "tomato", "watermelon", "yuzu")));
        }

        @Test
        @DisplayName("Test Method")
        void test_method() {
            final String input = """
                    class Foo {
                        bar(arg) {
                            print arg;
                        }
                    }
                    
                    var bar = Foo().bar;
                    print "got method";
                    bar("arg");""";

            LoxTest.run(input, new TestInterpreter(List.of("got method", "arg")));
        }

        @Test
        @DisplayName("Test Method Binds This")
        void test_method_binds_this() {
            final String input = """
                    class Foo {
                        sayName(a) {
                            print this.name;
                            print a;
                        }
                    }
                    
                    var foo1 = Foo();
                    foo1.name = "foo1";
                    
                    var foo2 = Foo();
                    foo2.name = "foo2";
                    
                    foo2.fn = foo1.sayName;
                    foo2.fn(1);""";

            LoxTest.run(input, new TestInterpreter(List.of("foo1", "1")));
        }

        @Test
        @DisplayName("Test On Instance")
        void test_on_instance() {
            final String input = """
                    class Foo {}
                    
                    var foo = Foo();
                    
                    print foo.bar = "bar value";
                    print foo.baz = "baz value";
                    
                    print foo.bar;
                    print foo.baz;""";

            LoxTest.run(input, new TestInterpreter(List.of("bar value", "baz value", "bar value", "baz value")));
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