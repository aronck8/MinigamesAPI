package com.aronck.minigamesapi.elements.map.buildrules;


/**
 *
 * The BuildRuleType determines if build rule has a negative or positive logic. If the BuildRule is negative it checks if
 * the Block action is allowed. If it is, the action gets checked by the next rule. If it isn't the action gets cancelled automatically.
 * So in order for an action to be process with negative Buildrules, every rule has to be true.
 *
 * If the BuildRule is positive, if any rule is true, it is going to overwrite every other existing rule
 *
 */
public enum BuildRuleType {

    POSITIVE,
    NEGATIVE

}
