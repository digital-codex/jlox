package dev.digitalcodex.lemur;

public interface CharacterStream {
    char read();
    void write(char out);

    CharacterEncoding encoding();
}