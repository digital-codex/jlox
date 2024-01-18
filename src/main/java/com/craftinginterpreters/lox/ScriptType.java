package com.craftinginterpreters.lox;

import java.util.HashMap;
import java.util.Map;

enum ScriptType {
    EMPTY_FILE_1,
    UNEXPECTED_CHARACTER_1,
    PRECEDENCE_1,
    INHERIT_SELF_1,
    REFERENCE_SELF_1,
    LOCAL_INHERIT_OTHER_1,
    EMPTY_1,
    LOCAL_INHERIT_SELF_1,
    INHERITED_METHOD_1,
    LOCAL_REFERENCE_SELF_1,
    AFTER_ELSE_1,
    IN_FUNCTION_1,
    RETURN_NIL_IF_NO_VALUE_1,
    AFTER_WHILE_1,
    AFTER_IF_1,
    AT_TOP_LEVEL_1,
    IN_METHOD_1,
    ERROR_AFTER_MULTILINE_1,
    UNTERMINATED_1,
    LITERALS_1,
    MULTILINE_1,
    NIL_1,
    BOOL_1,
    NUM_1,
    STRING_1,
    OBJECT_1,
    ZOO_BATCH_1,
    ZOO_1,
    BINARY_TREES_1,
    PROPERTIES_1,
    INVOCATION_1,
    TREES_1,
    METHOD_CALL_1,
    INSTANTIATION_1,
    STRING_EQUALITY_1,
    EQUALITY_1,
    FIB_1,
    ONLY_LINE_COMMENT_1,
    LINE_AT_EOF_1,
    UNICODE_1,
    ONLY_LINE_COMMENT_AND_LINE_1,
    THIS_IN_TOP_LEVEL_FUNCTION_1,
    THIS_IN_METHOD_1,
    THIS_AT_TOP_LEVEL_1,
    NESTED_CLOSURE_1,
    CLOSURE_1,
    NESTED_CLASS_1,
    UNUSED_LATER_CLOSURE_1,
    SHADOW_CLOSURE_WITH_LOCAL_1,
    CLOSE_OVER_LATER_VARIABLE_1,
    REFERENCE_CLOSURE_MULTIPLE_TIMES_1,
    CLOSED_CLOSURE_IN_FUNCTION_1,
    ASSIGN_TO_CLOSURE_1,
    OPEN_CLOSURE_IN_FUNCTION_1,
    NESTED_CLOSURE_2,
    REUSE_CLOSURE_SLOT_1,
    CLOSE_OVER_FUNCTION_PARAMETER_1,
    CLOSE_OVER_METHOD_PARAMETER_1,
    UNUSED_CLOSURE_1,
    ASSIGN_TO_SHADOWED_LATER_1,
    MANY_1,
    GET_ON_CLASS_1,
    METHOD_BINDS_THIS_1,
    GET_ON_BOOL_1,
    SET_EVALUATION_ORDER_1,
    CALL_FUNCTION_FIELD_1,
    GET_ON_FUNCTION_1,
    GET_AND_SET_METHOD_1,
    SET_ON_NIL_1,
    SET_ON_STRING_1,
    GET_ON_NUM_1,
    METHOD_1,
    SET_ON_FUNCTION_1,
    GET_ON_NIL_1,
    GET_ON_STRING_1,
    SET_ON_NUM_1,
    SET_ON_CLASS_1,
    UNDEFINED_1,
    ON_INSTANCE_1,
    CALL_NONFUNCTION_FIELD_1,
    SET_ON_BOOL_1,
    OR_1,
    AND_1,
    AND_TRUTH_1,
    OR_TRUTH_1,
    EXTRA_ARGUMENTS_1,
    DEFAULT_ARGUMENTS_1,
    RETURN_VALUE_1,
    CALL_INIT_EARLY_RETURN_1,
    CALL_INIT_EXPLICITLY_1,
    MISSING_ARGUMENTS_1,
    DEFAULT_1,
    EARLY_RETURN_1,
    INIT_NOT_METHOD_1,
    ARGUMENTS_1,
    RETURN_IN_NESTED_FUNCTION_1,
    $394_1,
    $40_1,
    EVALUATE_1,
    PARSE_1,
    INFIX_OPERATOR_1,
    PREFIX_OPERATOR_1,
    ASSOCIATIVITY_1,
    LOCAL_1,
    GLOBAL_1,
    GROUPING_1,
    TO_THIS_1,
    UNDEFINED_2,
    SYNTAX_1,
    RETURN_CLOSURE_1,
    STATEMENT_INCREMENT_1,
    FUN_IN_BODY_1,
    STATEMENT_CONDITION_1,
    SCOPE_1,
    CLASS_IN_BODY_1,
    CLOSURE_IN_BODY_1,
    VAR_IN_BODY_1,
    RETURN_INSIDE_1,
    STATEMENT_INITIALIZER_1,
    SYNTAX_2,
    CALL_OTHER_METHOD_1,
    EXTRA_ARGUMENTS_2,
    PARENTHESIZED_1,
    SUPER_WITHOUT_NAME_1,
    NO_SUPERCLASS_CALL_1,
    NO_SUPERCLASS_METHOD_1,
    INDIRECTLY_INHERITED_1,
    THIS_IN_SUPERCLASS_METHOD_1,
    REASSIGN_SUPERCLASS_1,
    MISSING_ARGUMENTS_2,
    SUPER_AT_TOP_LEVEL_1,
    NO_SUPERCLASS_BIND_1,
    BOUND_METHOD_1,
    SUPER_IN_INHERITED_METHOD_1,
    CLOSURE_2,
    SUPER_IN_TOP_LEVEL_FUNCTION_1,
    SUPER_WITHOUT_DOT_1,
    CALL_SAME_METHOD_1,
    CONSTRUCTOR_1,
    SUPER_IN_CLOSURE_IN_INHERITED_METHOD_1,
    WHITESPACE_1,
    IDENTIFIERS_1,
    KEYWORDS_1,
    STRINGS_1,
    PUNCTUATORS_1,
    NUMBERS_1,
    REFER_TO_NAME_1,
    EXTRA_ARGUMENTS_3,
    TOO_MANY_PARAMETERS_1,
    EMPTY_BLOCK_1,
    PRINT_BOUND_METHOD_1,
    MISSING_ARGUMENTS_3,
    ARITY_1,
    NOT_FOUND_1,
    TOO_MANY_ARGUMENTS_1,
    LITERAL_1,
    NOT_1,
    EQUALITY_2,
    STACK_OVERFLOW_1,
    NO_REUSE_CONSTANTS_1,
    TOO_MANY_CONSTANTS_1,
    TOO_MANY_LOCALS_1,
    TOO_MANY_UPVALUES_1,
    LOOP_TOO_LARGE_1,
    SHADOW_LOCAL_1,
    REDECLARE_GLOBAL_1,
    IN_NESTED_BLOCK_1,
    USE_THIS_AS_VAR_1,
    SHADOW_AND_LOCAL_1,
    USE_GLOBAL_IN_INITIALIZER_1,
    UNINITIALIZED_1,
    DUPLICATE_PARAMETER_1,
    SCOPE_REUSE_IN_DIFFERENT_BLOCKS_1,
    DUPLICATE_LOCAL_1,
    UNDEFINED_GLOBAL_1,
    USE_LOCAL_IN_INITIALIZER_1,
    COLLIDE_WITH_PARAMETER_1,
    EARLY_BOUND_1,
    USE_FALSE_AS_VAR_1,
    LOCAL_FROM_METHOD_1,
    SHADOW_GLOBAL_1,
    UNDEFINED_LOCAL_1,
    USE_NIL_AS_VAR_1,
    REDEFINE_GLOBAL_1,
    IN_MIDDLE_OF_BLOCK_1,
    UNREACHED_UNDEFINED_1,
    ADD_NIL_NIL_1,
    DIVIDE_1,
    SUBTRACT_NONNUM_NUM_1,
    ADD_1,
    GREATER_NUM_NONNUM_1,
    EQUALS_1,
    NEGATE_NONNUM_1,
    NOT_2,
    EQUALS_CLASS_1,
    GREATER_NONNUM_NUM_1,
    NOT_EQUALS_1,
    LESS_OR_EQUAL_NONNUM_NUM_1,
    DIVIDE_NUM_NONNUM_1,
    ADD_BOOL_NIL_1,
    MULTIPLY_1,
    NEGATE_1,
    ADD_BOOL_STRING_1,
    SUBTRACT_NUM_NONNUM_1,
    GREATER_OR_EQUAL_NONNUM_NUM_1,
    DIVIDE_NONNUM_NUM_1,
    SUBTRACT_1,
    LESS_NONNUM_NUM_1,
    LESS_NUM_NONNUM_1,
    NOT_CLASS_1,
    MULTIPLY_NUM_NONNUM_1,
    ADD_STRING_NIL_1,
    MULTIPLY_NONNUM_NUM_1,
    EQUALS_METHOD_1,
    ADD_NUM_NIL_1,
    GREATER_OR_EQUAL_NUM_NONNUM_1,
    ADD_BOOL_NUM_1,
    COMPARISON_1,
    LESS_OR_EQUAL_NUM_NONNUM_1,
    EXTRA_ARGUMENTS_4,
    PARAMETERS_1,
    RECURSION_1,
    MISSING_COMMA_IN_PARAMETERS_1,
    TOO_MANY_PARAMETERS_2,
    MISSING_ARGUMENTS_4,
    TOO_MANY_ARGUMENTS_2,
    LOCAL_RECURSION_1,
    PRINT_1,
    BODY_MUST_BE_BLOCK_1,
    NESTED_CALL_WITH_ARGUMENTS_1,
    LOCAL_MUTUAL_RECURSION_1,
    EMPTY_BODY_1,
    MUTUAL_RECURSION_1,
    MISSING_ARGUMENT_1,
    DECIMAL_POINT_AT_EOF_1,
    LITERALS_2,
    LEADING_DOT_1,
    TRAILING_DOT_1,
    NAN_EQUALITY_1,
    VAR_IN_ELSE_1,
    ELSE_1,
    CLASS_IN_ELSE_1,
    CLASS_IN_THEN_1,
    TRUTH_1,
    FUN_IN_ELSE_1,
    IF_1,
    DANGLING_ELSE_1,
    VAR_IN_THEN_1,
    FUN_IN_THEN_1,
    EMPTY_2,
    SCOPE_2,
    RETURN_CLOSURE_2,
    FUN_IN_BODY_2,
    CLASS_IN_BODY_2,
    CLOSURE_IN_BODY_2,
    VAR_IN_BODY_2,
    RETURN_INSIDE_2,
    SYNTAX_3,
    INHERIT_FROM_NUMBER_1,
    INHERIT_METHODS_1,
    INHERIT_FROM_FUNCTION_1,
    PARENTHESIZED_SUPERCLASS_1,
    SET_FIELDS_FROM_BASE_CLASS_1,
    INHERIT_FROM_NIL_1,
    CONSTRUCTOR_2;

    private static final Map<ScriptType, String> lookup = new HashMap<>();
    static {
        lookup.put( EMPTY_FILE_1, "/home/treyvon/src/craftinginterpreters/test/empty_file.lox" );
        lookup.put( UNEXPECTED_CHARACTER_1, "/home/treyvon/src/craftinginterpreters/test/unexpected_character.lox" );
        lookup.put( PRECEDENCE_1, "/home/treyvon/src/craftinginterpreters/test/precedence.lox" );
        lookup.put( INHERIT_SELF_1, "/home/treyvon/src/craftinginterpreters/test/class/inherit_self.lox" );
        lookup.put( REFERENCE_SELF_1, "/home/treyvon/src/craftinginterpreters/test/class/reference_self.lox" );
        lookup.put( LOCAL_INHERIT_OTHER_1, "/home/treyvon/src/craftinginterpreters/test/class/local_inherit_other.lox" );
        lookup.put( EMPTY_1, "/home/treyvon/src/craftinginterpreters/test/class/empty.lox" );
        lookup.put( LOCAL_INHERIT_SELF_1, "/home/treyvon/src/craftinginterpreters/test/class/local_inherit_self.lox" );
        lookup.put( INHERITED_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/class/inherited_method.lox" );
        lookup.put( LOCAL_REFERENCE_SELF_1, "/home/treyvon/src/craftinginterpreters/test/class/local_reference_self.lox" );
        lookup.put( AFTER_ELSE_1, "/home/treyvon/src/craftinginterpreters/test/return/after_else.lox" );
        lookup.put( IN_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/return/in_function.lox" );
        lookup.put( RETURN_NIL_IF_NO_VALUE_1, "/home/treyvon/src/craftinginterpreters/test/return/return_nil_if_no_value.lox" );
        lookup.put( AFTER_WHILE_1, "/home/treyvon/src/craftinginterpreters/test/return/after_while.lox" );
        lookup.put( AFTER_IF_1, "/home/treyvon/src/craftinginterpreters/test/return/after_if.lox" );
        lookup.put( AT_TOP_LEVEL_1, "/home/treyvon/src/craftinginterpreters/test/return/at_top_level.lox" );
        lookup.put( IN_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/return/in_method.lox" );
        lookup.put( ERROR_AFTER_MULTILINE_1, "/home/treyvon/src/craftinginterpreters/test/string/error_after_multiline.lox" );
        lookup.put( UNTERMINATED_1, "/home/treyvon/src/craftinginterpreters/test/string/unterminated.lox" );
        lookup.put( LITERALS_1, "/home/treyvon/src/craftinginterpreters/test/string/literals.lox" );
        lookup.put( MULTILINE_1, "/home/treyvon/src/craftinginterpreters/test/string/multiline.lox" );
        lookup.put( NIL_1, "/home/treyvon/src/craftinginterpreters/test/call/nil.lox" );
        lookup.put( BOOL_1, "/home/treyvon/src/craftinginterpreters/test/call/bool.lox" );
        lookup.put( NUM_1, "/home/treyvon/src/craftinginterpreters/test/call/num.lox" );
        lookup.put( STRING_1, "/home/treyvon/src/craftinginterpreters/test/call/string.lox" );
        lookup.put( OBJECT_1, "/home/treyvon/src/craftinginterpreters/test/call/object.lox" );
        lookup.put( ZOO_BATCH_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/zoo_batch.lox" );
        lookup.put( ZOO_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/zoo.lox" );
        lookup.put( BINARY_TREES_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/binary_trees.lox" );
        lookup.put( PROPERTIES_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/properties.lox" );
        lookup.put( INVOCATION_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/invocation.lox" );
        lookup.put( TREES_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/trees.lox" );
        lookup.put( METHOD_CALL_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/method_call.lox" );
        lookup.put( INSTANTIATION_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/instantiation.lox" );
        lookup.put( STRING_EQUALITY_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/string_equality.lox" );
        lookup.put( EQUALITY_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/equality.lox" );
        lookup.put( FIB_1, "/home/treyvon/src/craftinginterpreters/test/benchmark/fib.lox" );
        lookup.put( ONLY_LINE_COMMENT_1, "/home/treyvon/src/craftinginterpreters/test/comments/only_line_comment.lox" );
        lookup.put( LINE_AT_EOF_1, "/home/treyvon/src/craftinginterpreters/test/comments/line_at_eof.lox" );
        lookup.put( UNICODE_1, "/home/treyvon/src/craftinginterpreters/test/comments/unicode.lox" );
        lookup.put( ONLY_LINE_COMMENT_AND_LINE_1, "/home/treyvon/src/craftinginterpreters/test/comments/only_line_comment_and_line.lox" );
        lookup.put( THIS_IN_TOP_LEVEL_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/this/this_in_top_level_function.lox" );
        lookup.put( THIS_IN_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/this/this_in_method.lox" );
        lookup.put( THIS_AT_TOP_LEVEL_1, "/home/treyvon/src/craftinginterpreters/test/this/this_at_top_level.lox" );
        lookup.put( NESTED_CLOSURE_1, "/home/treyvon/src/craftinginterpreters/test/this/nested_closure.lox" );
        lookup.put( CLOSURE_1, "/home/treyvon/src/craftinginterpreters/test/this/closure.lox" );
        lookup.put( NESTED_CLASS_1, "/home/treyvon/src/craftinginterpreters/test/this/nested_class.lox" );
        lookup.put( UNUSED_LATER_CLOSURE_1, "/home/treyvon/src/craftinginterpreters/test/closure/unused_later_closure.lox" );
        lookup.put( SHADOW_CLOSURE_WITH_LOCAL_1, "/home/treyvon/src/craftinginterpreters/test/closure/shadow_closure_with_local.lox" );
        lookup.put( CLOSE_OVER_LATER_VARIABLE_1, "/home/treyvon/src/craftinginterpreters/test/closure/close_over_later_variable.lox" );
        lookup.put( REFERENCE_CLOSURE_MULTIPLE_TIMES_1, "/home/treyvon/src/craftinginterpreters/test/closure/reference_closure_multiple_times.lox" );
        lookup.put( CLOSED_CLOSURE_IN_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/closure/closed_closure_in_function.lox" );
        lookup.put( ASSIGN_TO_CLOSURE_1, "/home/treyvon/src/craftinginterpreters/test/closure/assign_to_closure.lox" );
        lookup.put( OPEN_CLOSURE_IN_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/closure/open_closure_in_function.lox" );
        lookup.put( NESTED_CLOSURE_2, "/home/treyvon/src/craftinginterpreters/test/closure/nested_closure.lox" );
        lookup.put( REUSE_CLOSURE_SLOT_1, "/home/treyvon/src/craftinginterpreters/test/closure/reuse_closure_slot.lox" );
        lookup.put( CLOSE_OVER_FUNCTION_PARAMETER_1, "/home/treyvon/src/craftinginterpreters/test/closure/close_over_function_parameter.lox" );
        lookup.put( CLOSE_OVER_METHOD_PARAMETER_1, "/home/treyvon/src/craftinginterpreters/test/closure/close_over_method_parameter.lox" );
        lookup.put( UNUSED_CLOSURE_1, "/home/treyvon/src/craftinginterpreters/test/closure/unused_closure.lox" );
        lookup.put( ASSIGN_TO_SHADOWED_LATER_1, "/home/treyvon/src/craftinginterpreters/test/closure/assign_to_shadowed_later.lox" );
        lookup.put( MANY_1, "/home/treyvon/src/craftinginterpreters/test/field/many.lox" );
        lookup.put( GET_ON_CLASS_1, "/home/treyvon/src/craftinginterpreters/test/field/get_on_class.lox" );
        lookup.put( METHOD_BINDS_THIS_1, "/home/treyvon/src/craftinginterpreters/test/field/method_binds_this.lox" );
        lookup.put( GET_ON_BOOL_1, "/home/treyvon/src/craftinginterpreters/test/field/get_on_bool.lox" );
        lookup.put( SET_EVALUATION_ORDER_1, "/home/treyvon/src/craftinginterpreters/test/field/set_evaluation_order.lox" );
        lookup.put( CALL_FUNCTION_FIELD_1, "/home/treyvon/src/craftinginterpreters/test/field/call_function_field.lox" );
        lookup.put( GET_ON_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/field/get_on_function.lox" );
        lookup.put( GET_AND_SET_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/field/get_and_set_method.lox" );
        lookup.put( SET_ON_NIL_1, "/home/treyvon/src/craftinginterpreters/test/field/set_on_nil.lox" );
        lookup.put( SET_ON_STRING_1, "/home/treyvon/src/craftinginterpreters/test/field/set_on_string.lox" );
        lookup.put( GET_ON_NUM_1, "/home/treyvon/src/craftinginterpreters/test/field/get_on_num.lox" );
        lookup.put( METHOD_1, "/home/treyvon/src/craftinginterpreters/test/field/method.lox" );
        lookup.put( SET_ON_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/field/set_on_function.lox" );
        lookup.put( GET_ON_NIL_1, "/home/treyvon/src/craftinginterpreters/test/field/get_on_nil.lox" );
        lookup.put( GET_ON_STRING_1, "/home/treyvon/src/craftinginterpreters/test/field/get_on_string.lox" );
        lookup.put( SET_ON_NUM_1, "/home/treyvon/src/craftinginterpreters/test/field/set_on_num.lox" );
        lookup.put( SET_ON_CLASS_1, "/home/treyvon/src/craftinginterpreters/test/field/set_on_class.lox" );
        lookup.put( UNDEFINED_1, "/home/treyvon/src/craftinginterpreters/test/field/undefined.lox" );
        lookup.put( ON_INSTANCE_1, "/home/treyvon/src/craftinginterpreters/test/field/on_instance.lox" );
        lookup.put( CALL_NONFUNCTION_FIELD_1, "/home/treyvon/src/craftinginterpreters/test/field/call_nonfunction_field.lox" );
        lookup.put( SET_ON_BOOL_1, "/home/treyvon/src/craftinginterpreters/test/field/set_on_bool.lox" );
        lookup.put( OR_1, "/home/treyvon/src/craftinginterpreters/test/logical_operator/or.lox" );
        lookup.put( AND_1, "/home/treyvon/src/craftinginterpreters/test/logical_operator/and.lox" );
        lookup.put( AND_TRUTH_1, "/home/treyvon/src/craftinginterpreters/test/logical_operator/and_truth.lox" );
        lookup.put( OR_TRUTH_1, "/home/treyvon/src/craftinginterpreters/test/logical_operator/or_truth.lox" );
        lookup.put( EXTRA_ARGUMENTS_1, "/home/treyvon/src/craftinginterpreters/test/constructor/extra_arguments.lox" );
        lookup.put( DEFAULT_ARGUMENTS_1, "/home/treyvon/src/craftinginterpreters/test/constructor/default_arguments.lox" );
        lookup.put( RETURN_VALUE_1, "/home/treyvon/src/craftinginterpreters/test/constructor/return_value.lox" );
        lookup.put( CALL_INIT_EARLY_RETURN_1, "/home/treyvon/src/craftinginterpreters/test/constructor/call_init_early_return.lox" );
        lookup.put( CALL_INIT_EXPLICITLY_1, "/home/treyvon/src/craftinginterpreters/test/constructor/call_init_explicitly.lox" );
        lookup.put( MISSING_ARGUMENTS_1, "/home/treyvon/src/craftinginterpreters/test/constructor/missing_arguments.lox" );
        lookup.put( DEFAULT_1, "/home/treyvon/src/craftinginterpreters/test/constructor/default.lox" );
        lookup.put( EARLY_RETURN_1, "/home/treyvon/src/craftinginterpreters/test/constructor/early_return.lox" );
        lookup.put( INIT_NOT_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/constructor/init_not_method.lox" );
        lookup.put( ARGUMENTS_1, "/home/treyvon/src/craftinginterpreters/test/constructor/arguments.lox" );
        lookup.put( RETURN_IN_NESTED_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/constructor/return_in_nested_function.lox" );
        lookup.put( $394_1, "/home/treyvon/src/craftinginterpreters/test/regression/394.lox" );
        lookup.put( $40_1, "/home/treyvon/src/craftinginterpreters/test/regression/40.lox" );
        lookup.put( EVALUATE_1, "/home/treyvon/src/craftinginterpreters/test/expressions/evaluate.lox" );
        lookup.put( PARSE_1, "/home/treyvon/src/craftinginterpreters/test/expressions/parse.lox" );
        lookup.put( INFIX_OPERATOR_1, "/home/treyvon/src/craftinginterpreters/test/assignment/infix_operator.lox" );
        lookup.put( PREFIX_OPERATOR_1, "/home/treyvon/src/craftinginterpreters/test/assignment/prefix_operator.lox" );
        lookup.put( ASSOCIATIVITY_1, "/home/treyvon/src/craftinginterpreters/test/assignment/associativity.lox" );
        lookup.put( LOCAL_1, "/home/treyvon/src/craftinginterpreters/test/assignment/local.lox" );
        lookup.put( GLOBAL_1, "/home/treyvon/src/craftinginterpreters/test/assignment/global.lox" );
        lookup.put( GROUPING_1, "/home/treyvon/src/craftinginterpreters/test/assignment/grouping.lox" );
        lookup.put( TO_THIS_1, "/home/treyvon/src/craftinginterpreters/test/assignment/to_this.lox" );
        lookup.put( UNDEFINED_2, "/home/treyvon/src/craftinginterpreters/test/assignment/undefined.lox" );
        lookup.put( SYNTAX_1, "/home/treyvon/src/craftinginterpreters/test/assignment/syntax.lox" );
        lookup.put( RETURN_CLOSURE_1, "/home/treyvon/src/craftinginterpreters/test/for/return_closure.lox" );
        lookup.put( STATEMENT_INCREMENT_1, "/home/treyvon/src/craftinginterpreters/test/for/statement_increment.lox" );
        lookup.put( FUN_IN_BODY_1, "/home/treyvon/src/craftinginterpreters/test/for/fun_in_body.lox" );
        lookup.put( STATEMENT_CONDITION_1, "/home/treyvon/src/craftinginterpreters/test/for/statement_condition.lox" );
        lookup.put( SCOPE_1, "/home/treyvon/src/craftinginterpreters/test/for/scope.lox" );
        lookup.put( CLASS_IN_BODY_1, "/home/treyvon/src/craftinginterpreters/test/for/class_in_body.lox" );
        lookup.put( CLOSURE_IN_BODY_1, "/home/treyvon/src/craftinginterpreters/test/for/closure_in_body.lox" );
        lookup.put( VAR_IN_BODY_1, "/home/treyvon/src/craftinginterpreters/test/for/var_in_body.lox" );
        lookup.put( RETURN_INSIDE_1, "/home/treyvon/src/craftinginterpreters/test/for/return_inside.lox" );
        lookup.put( STATEMENT_INITIALIZER_1, "/home/treyvon/src/craftinginterpreters/test/for/statement_initializer.lox" );
        lookup.put( SYNTAX_2, "/home/treyvon/src/craftinginterpreters/test/for/syntax.lox" );
        lookup.put( CALL_OTHER_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/super/call_other_method.lox" );
        lookup.put( EXTRA_ARGUMENTS_2, "/home/treyvon/src/craftinginterpreters/test/super/extra_arguments.lox" );
        lookup.put( PARENTHESIZED_1, "/home/treyvon/src/craftinginterpreters/test/super/parenthesized.lox" );
        lookup.put( SUPER_WITHOUT_NAME_1, "/home/treyvon/src/craftinginterpreters/test/super/super_without_name.lox" );
        lookup.put( NO_SUPERCLASS_CALL_1, "/home/treyvon/src/craftinginterpreters/test/super/no_superclass_call.lox" );
        lookup.put( NO_SUPERCLASS_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/super/no_superclass_method.lox" );
        lookup.put( INDIRECTLY_INHERITED_1, "/home/treyvon/src/craftinginterpreters/test/super/indirectly_inherited.lox" );
        lookup.put( THIS_IN_SUPERCLASS_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/super/this_in_superclass_method.lox" );
        lookup.put( REASSIGN_SUPERCLASS_1, "/home/treyvon/src/craftinginterpreters/test/super/reassign_superclass.lox" );
        lookup.put( MISSING_ARGUMENTS_2, "/home/treyvon/src/craftinginterpreters/test/super/missing_arguments.lox" );
        lookup.put( SUPER_AT_TOP_LEVEL_1, "/home/treyvon/src/craftinginterpreters/test/super/super_at_top_level.lox" );
        lookup.put( NO_SUPERCLASS_BIND_1, "/home/treyvon/src/craftinginterpreters/test/super/no_superclass_bind.lox" );
        lookup.put( BOUND_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/super/bound_method.lox" );
        lookup.put( SUPER_IN_INHERITED_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/super/super_in_inherited_method.lox" );
        lookup.put( CLOSURE_2, "/home/treyvon/src/craftinginterpreters/test/super/closure.lox" );
        lookup.put( SUPER_IN_TOP_LEVEL_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/super/super_in_top_level_function.lox" );
        lookup.put( SUPER_WITHOUT_DOT_1, "/home/treyvon/src/craftinginterpreters/test/super/super_without_dot.lox" );
        lookup.put( CALL_SAME_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/super/call_same_method.lox" );
        lookup.put( CONSTRUCTOR_1, "/home/treyvon/src/craftinginterpreters/test/super/constructor.lox" );
        lookup.put( SUPER_IN_CLOSURE_IN_INHERITED_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/super/super_in_closure_in_inherited_method.lox" );
        lookup.put( WHITESPACE_1, "/home/treyvon/src/craftinginterpreters/test/scanning/whitespace.lox" );
        lookup.put( IDENTIFIERS_1, "/home/treyvon/src/craftinginterpreters/test/scanning/identifiers.lox" );
        lookup.put( KEYWORDS_1, "/home/treyvon/src/craftinginterpreters/test/scanning/keywords.lox" );
        lookup.put( STRINGS_1, "/home/treyvon/src/craftinginterpreters/test/scanning/strings.lox" );
        lookup.put( PUNCTUATORS_1, "/home/treyvon/src/craftinginterpreters/test/scanning/punctuators.lox" );
        lookup.put( NUMBERS_1, "/home/treyvon/src/craftinginterpreters/test/scanning/numbers.lox" );
        lookup.put( REFER_TO_NAME_1, "/home/treyvon/src/craftinginterpreters/test/method/refer_to_name.lox" );
        lookup.put( EXTRA_ARGUMENTS_3, "/home/treyvon/src/craftinginterpreters/test/method/extra_arguments.lox" );
        lookup.put( TOO_MANY_PARAMETERS_1, "/home/treyvon/src/craftinginterpreters/test/method/too_many_parameters.lox" );
        lookup.put( EMPTY_BLOCK_1, "/home/treyvon/src/craftinginterpreters/test/method/empty_block.lox" );
        lookup.put( PRINT_BOUND_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/method/print_bound_method.lox" );
        lookup.put( MISSING_ARGUMENTS_3, "/home/treyvon/src/craftinginterpreters/test/method/missing_arguments.lox" );
        lookup.put( ARITY_1, "/home/treyvon/src/craftinginterpreters/test/method/arity.lox" );
        lookup.put( NOT_FOUND_1, "/home/treyvon/src/craftinginterpreters/test/method/not_found.lox" );
        lookup.put( TOO_MANY_ARGUMENTS_1, "/home/treyvon/src/craftinginterpreters/test/method/too_many_arguments.lox" );
        lookup.put( LITERAL_1, "/home/treyvon/src/craftinginterpreters/test/nil/literal.lox" );
        lookup.put( NOT_1, "/home/treyvon/src/craftinginterpreters/test/bool/not.lox" );
        lookup.put( EQUALITY_2, "/home/treyvon/src/craftinginterpreters/test/bool/equality.lox" );
        lookup.put( STACK_OVERFLOW_1, "/home/treyvon/src/craftinginterpreters/test/limit/stack_overflow.lox" );
        lookup.put( NO_REUSE_CONSTANTS_1, "/home/treyvon/src/craftinginterpreters/test/limit/no_reuse_constants.lox" );
        lookup.put( TOO_MANY_CONSTANTS_1, "/home/treyvon/src/craftinginterpreters/test/limit/too_many_constants.lox" );
        lookup.put( TOO_MANY_LOCALS_1, "/home/treyvon/src/craftinginterpreters/test/limit/too_many_locals.lox" );
        lookup.put( TOO_MANY_UPVALUES_1, "/home/treyvon/src/craftinginterpreters/test/limit/too_many_upvalues.lox" );
        lookup.put( LOOP_TOO_LARGE_1, "/home/treyvon/src/craftinginterpreters/test/limit/loop_too_large.lox" );
        lookup.put( SHADOW_LOCAL_1, "/home/treyvon/src/craftinginterpreters/test/variable/shadow_local.lox" );
        lookup.put( REDECLARE_GLOBAL_1, "/home/treyvon/src/craftinginterpreters/test/variable/redeclare_global.lox" );
        lookup.put( IN_NESTED_BLOCK_1, "/home/treyvon/src/craftinginterpreters/test/variable/in_nested_block.lox" );
        lookup.put( USE_THIS_AS_VAR_1, "/home/treyvon/src/craftinginterpreters/test/variable/use_this_as_var.lox" );
        lookup.put( SHADOW_AND_LOCAL_1, "/home/treyvon/src/craftinginterpreters/test/variable/shadow_and_local.lox" );
        lookup.put( USE_GLOBAL_IN_INITIALIZER_1, "/home/treyvon/src/craftinginterpreters/test/variable/use_global_in_initializer.lox" );
        lookup.put( UNINITIALIZED_1, "/home/treyvon/src/craftinginterpreters/test/variable/uninitialized.lox" );
        lookup.put( DUPLICATE_PARAMETER_1, "/home/treyvon/src/craftinginterpreters/test/variable/duplicate_parameter.lox" );
        lookup.put( SCOPE_REUSE_IN_DIFFERENT_BLOCKS_1, "/home/treyvon/src/craftinginterpreters/test/variable/scope_reuse_in_different_blocks.lox" );
        lookup.put( DUPLICATE_LOCAL_1, "/home/treyvon/src/craftinginterpreters/test/variable/duplicate_local.lox" );
        lookup.put( UNDEFINED_GLOBAL_1, "/home/treyvon/src/craftinginterpreters/test/variable/undefined_global.lox" );
        lookup.put( USE_LOCAL_IN_INITIALIZER_1, "/home/treyvon/src/craftinginterpreters/test/variable/use_local_in_initializer.lox" );
        lookup.put( COLLIDE_WITH_PARAMETER_1, "/home/treyvon/src/craftinginterpreters/test/variable/collide_with_parameter.lox" );
        lookup.put( EARLY_BOUND_1, "/home/treyvon/src/craftinginterpreters/test/variable/early_bound.lox" );
        lookup.put( USE_FALSE_AS_VAR_1, "/home/treyvon/src/craftinginterpreters/test/variable/use_false_as_var.lox" );
        lookup.put( LOCAL_FROM_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/variable/local_from_method.lox" );
        lookup.put( SHADOW_GLOBAL_1, "/home/treyvon/src/craftinginterpreters/test/variable/shadow_global.lox" );
        lookup.put( UNDEFINED_LOCAL_1, "/home/treyvon/src/craftinginterpreters/test/variable/undefined_local.lox" );
        lookup.put( USE_NIL_AS_VAR_1, "/home/treyvon/src/craftinginterpreters/test/variable/use_nil_as_var.lox" );
        lookup.put( REDEFINE_GLOBAL_1, "/home/treyvon/src/craftinginterpreters/test/variable/redefine_global.lox" );
        lookup.put( IN_MIDDLE_OF_BLOCK_1, "/home/treyvon/src/craftinginterpreters/test/variable/in_middle_of_block.lox" );
        lookup.put( UNREACHED_UNDEFINED_1, "/home/treyvon/src/craftinginterpreters/test/variable/unreached_undefined.lox" );
        lookup.put( ADD_NIL_NIL_1, "/home/treyvon/src/craftinginterpreters/test/operator/add_nil_nil.lox" );
        lookup.put( DIVIDE_1, "/home/treyvon/src/craftinginterpreters/test/operator/divide.lox" );
        lookup.put( SUBTRACT_NONNUM_NUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/subtract_nonnum_num.lox" );
        lookup.put( ADD_1, "/home/treyvon/src/craftinginterpreters/test/operator/add.lox" );
        lookup.put( GREATER_NUM_NONNUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/greater_num_nonnum.lox" );
        lookup.put( EQUALS_1, "/home/treyvon/src/craftinginterpreters/test/operator/equals.lox" );
        lookup.put( NEGATE_NONNUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/negate_nonnum.lox" );
        lookup.put( NOT_2, "/home/treyvon/src/craftinginterpreters/test/operator/not.lox" );
        lookup.put( EQUALS_CLASS_1, "/home/treyvon/src/craftinginterpreters/test/operator/equals_class.lox" );
        lookup.put( GREATER_NONNUM_NUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/greater_nonnum_num.lox" );
        lookup.put( NOT_EQUALS_1, "/home/treyvon/src/craftinginterpreters/test/operator/not_equals.lox" );
        lookup.put( LESS_OR_EQUAL_NONNUM_NUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/less_or_equal_nonnum_num.lox" );
        lookup.put( DIVIDE_NUM_NONNUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/divide_num_nonnum.lox" );
        lookup.put( ADD_BOOL_NIL_1, "/home/treyvon/src/craftinginterpreters/test/operator/add_bool_nil.lox" );
        lookup.put( MULTIPLY_1, "/home/treyvon/src/craftinginterpreters/test/operator/multiply.lox" );
        lookup.put( NEGATE_1, "/home/treyvon/src/craftinginterpreters/test/operator/negate.lox" );
        lookup.put( ADD_BOOL_STRING_1, "/home/treyvon/src/craftinginterpreters/test/operator/add_bool_string.lox" );
        lookup.put( SUBTRACT_NUM_NONNUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/subtract_num_nonnum.lox" );
        lookup.put( GREATER_OR_EQUAL_NONNUM_NUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/greater_or_equal_nonnum_num.lox" );
        lookup.put( DIVIDE_NONNUM_NUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/divide_nonnum_num.lox" );
        lookup.put( SUBTRACT_1, "/home/treyvon/src/craftinginterpreters/test/operator/subtract.lox" );
        lookup.put( LESS_NONNUM_NUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/less_nonnum_num.lox" );
        lookup.put( LESS_NUM_NONNUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/less_num_nonnum.lox" );
        lookup.put( NOT_CLASS_1, "/home/treyvon/src/craftinginterpreters/test/operator/not_class.lox" );
        lookup.put( MULTIPLY_NUM_NONNUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/multiply_num_nonnum.lox" );
        lookup.put( ADD_STRING_NIL_1, "/home/treyvon/src/craftinginterpreters/test/operator/add_string_nil.lox" );
        lookup.put( MULTIPLY_NONNUM_NUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/multiply_nonnum_num.lox" );
        lookup.put( EQUALS_METHOD_1, "/home/treyvon/src/craftinginterpreters/test/operator/equals_method.lox" );
        lookup.put( ADD_NUM_NIL_1, "/home/treyvon/src/craftinginterpreters/test/operator/add_num_nil.lox" );
        lookup.put( GREATER_OR_EQUAL_NUM_NONNUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/greater_or_equal_num_nonnum.lox" );
        lookup.put( ADD_BOOL_NUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/add_bool_num.lox" );
        lookup.put( COMPARISON_1, "/home/treyvon/src/craftinginterpreters/test/operator/comparison.lox" );
        lookup.put( LESS_OR_EQUAL_NUM_NONNUM_1, "/home/treyvon/src/craftinginterpreters/test/operator/less_or_equal_num_nonnum.lox" );
        lookup.put( EXTRA_ARGUMENTS_4, "/home/treyvon/src/craftinginterpreters/test/function/extra_arguments.lox" );
        lookup.put( PARAMETERS_1, "/home/treyvon/src/craftinginterpreters/test/function/parameters.lox" );
        lookup.put( RECURSION_1, "/home/treyvon/src/craftinginterpreters/test/function/recursion.lox" );
        lookup.put( MISSING_COMMA_IN_PARAMETERS_1, "/home/treyvon/src/craftinginterpreters/test/function/missing_comma_in_parameters.lox" );
        lookup.put( TOO_MANY_PARAMETERS_2, "/home/treyvon/src/craftinginterpreters/test/function/too_many_parameters.lox" );
        lookup.put( MISSING_ARGUMENTS_4, "/home/treyvon/src/craftinginterpreters/test/function/missing_arguments.lox" );
        lookup.put( TOO_MANY_ARGUMENTS_2, "/home/treyvon/src/craftinginterpreters/test/function/too_many_arguments.lox" );
        lookup.put( LOCAL_RECURSION_1, "/home/treyvon/src/craftinginterpreters/test/function/local_recursion.lox" );
        lookup.put( PRINT_1, "/home/treyvon/src/craftinginterpreters/test/function/print.lox" );
        lookup.put( BODY_MUST_BE_BLOCK_1, "/home/treyvon/src/craftinginterpreters/test/function/body_must_be_block.lox" );
        lookup.put( NESTED_CALL_WITH_ARGUMENTS_1, "/home/treyvon/src/craftinginterpreters/test/function/nested_call_with_arguments.lox" );
        lookup.put( LOCAL_MUTUAL_RECURSION_1, "/home/treyvon/src/craftinginterpreters/test/function/local_mutual_recursion.lox" );
        lookup.put( EMPTY_BODY_1, "/home/treyvon/src/craftinginterpreters/test/function/empty_body.lox" );
        lookup.put( MUTUAL_RECURSION_1, "/home/treyvon/src/craftinginterpreters/test/function/mutual_recursion.lox" );
        lookup.put( MISSING_ARGUMENT_1, "/home/treyvon/src/craftinginterpreters/test/print/missing_argument.lox" );
        lookup.put( DECIMAL_POINT_AT_EOF_1, "/home/treyvon/src/craftinginterpreters/test/number/decimal_point_at_eof.lox" );
        lookup.put( LITERALS_2, "/home/treyvon/src/craftinginterpreters/test/number/literals.lox" );
        lookup.put( LEADING_DOT_1, "/home/treyvon/src/craftinginterpreters/test/number/leading_dot.lox" );
        lookup.put( TRAILING_DOT_1, "/home/treyvon/src/craftinginterpreters/test/number/trailing_dot.lox" );
        lookup.put( NAN_EQUALITY_1, "/home/treyvon/src/craftinginterpreters/test/number/nan_equality.lox" );
        lookup.put( VAR_IN_ELSE_1, "/home/treyvon/src/craftinginterpreters/test/if/var_in_else.lox" );
        lookup.put( ELSE_1, "/home/treyvon/src/craftinginterpreters/test/if/else.lox" );
        lookup.put( CLASS_IN_ELSE_1, "/home/treyvon/src/craftinginterpreters/test/if/class_in_else.lox" );
        lookup.put( CLASS_IN_THEN_1, "/home/treyvon/src/craftinginterpreters/test/if/class_in_then.lox" );
        lookup.put( TRUTH_1, "/home/treyvon/src/craftinginterpreters/test/if/truth.lox" );
        lookup.put( FUN_IN_ELSE_1, "/home/treyvon/src/craftinginterpreters/test/if/fun_in_else.lox" );
        lookup.put( IF_1, "/home/treyvon/src/craftinginterpreters/test/if/if.lox" );
        lookup.put( DANGLING_ELSE_1, "/home/treyvon/src/craftinginterpreters/test/if/dangling_else.lox" );
        lookup.put( VAR_IN_THEN_1, "/home/treyvon/src/craftinginterpreters/test/if/var_in_then.lox" );
        lookup.put( FUN_IN_THEN_1, "/home/treyvon/src/craftinginterpreters/test/if/fun_in_then.lox" );
        lookup.put( EMPTY_2, "/home/treyvon/src/craftinginterpreters/test/block/empty.lox" );
        lookup.put( SCOPE_2, "/home/treyvon/src/craftinginterpreters/test/block/scope.lox" );
        lookup.put( RETURN_CLOSURE_2, "/home/treyvon/src/craftinginterpreters/test/while/return_closure.lox" );
        lookup.put( FUN_IN_BODY_2, "/home/treyvon/src/craftinginterpreters/test/while/fun_in_body.lox" );
        lookup.put( CLASS_IN_BODY_2, "/home/treyvon/src/craftinginterpreters/test/while/class_in_body.lox" );
        lookup.put( CLOSURE_IN_BODY_2, "/home/treyvon/src/craftinginterpreters/test/while/closure_in_body.lox" );
        lookup.put( VAR_IN_BODY_2, "/home/treyvon/src/craftinginterpreters/test/while/var_in_body.lox" );
        lookup.put( RETURN_INSIDE_2, "/home/treyvon/src/craftinginterpreters/test/while/return_inside.lox" );
        lookup.put( SYNTAX_3, "/home/treyvon/src/craftinginterpreters/test/while/syntax.lox" );
        lookup.put( INHERIT_FROM_NUMBER_1, "/home/treyvon/src/craftinginterpreters/test/inheritance/inherit_from_number.lox" );
        lookup.put( INHERIT_METHODS_1, "/home/treyvon/src/craftinginterpreters/test/inheritance/inherit_methods.lox" );
        lookup.put( INHERIT_FROM_FUNCTION_1, "/home/treyvon/src/craftinginterpreters/test/inheritance/inherit_from_function.lox" );
        lookup.put( PARENTHESIZED_SUPERCLASS_1, "/home/treyvon/src/craftinginterpreters/test/inheritance/parenthesized_superclass.lox" );
        lookup.put( SET_FIELDS_FROM_BASE_CLASS_1, "/home/treyvon/src/craftinginterpreters/test/inheritance/set_fields_from_base_class.lox" );
        lookup.put( INHERIT_FROM_NIL_1, "/home/treyvon/src/craftinginterpreters/test/inheritance/inherit_from_nil.lox" );
        lookup.put( CONSTRUCTOR_2, "/home/treyvon/src/craftinginterpreters/test/inheritance/constructor.lox" );
    }

    public static String lookup(ScriptType type) {
        return ScriptType.lookup.get(type);
    }
}
