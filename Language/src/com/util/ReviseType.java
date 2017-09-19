package com.util;

public enum ReviseType {
    ALL("All"),
    NEW("New"),
    OLD("Old"),
    WEAK("Weak"),
    WRONGS("Wrongs"),
    SHORT("Short"),
    RANDOM("Random");

    private String name;

    ReviseType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ReviseType get(String name) {
        for (ReviseType i : values()) {
            if (i.getName().equals(name)) {
                return i;
            }
        }
        throw new IllegalArgumentException("No such revise type");
    }

    public static String[] names() {
        String[] strings = new String[values().length];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = values()[i].getName();
        }
        return strings;
    }
}
