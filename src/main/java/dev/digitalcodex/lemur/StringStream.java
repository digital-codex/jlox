package dev.digitalcodex.lemur;

public final class StringStream implements CharacterStream {
    private final String source;
    private int current = 0;

    public StringStream(String source) {
        this.source = source;
    }

    public char read() {
        return this.source.charAt(this.current++);
    }

    public void write(char out) {

    }
}