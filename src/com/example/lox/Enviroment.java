package src.com.example.lox;

import java.util.Map;
import java.util.HashMap;

class Enviroment {
    final Enviroment enclosing;
    private final Map<String, Object> values = new HashMap<>();

    Enviroment() {
        enclosing = null;
    }

    Enviroment(Enviroment enclosing) {
        this.enclosing = enclosing;
    }
    
    void define(String name, Object value) { values.put(name, value); }

    Object get(Token name) {
        if (values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
        }

        if (enclosing != null) return enclosing.get(name); // recursively walking down the chain

        throw new RuntimeError(name, 
            "Undefined variable '" + name.lexeme + "'.");
    }

    void assign(Token name, Object value) {
        if (values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
            return;
        }

        if (enclosing != null) { 
            enclosing.assign(name, value); // recursively walking down the chain
            return;
        }

        throw new RuntimeError(name, 
            "Undefined variable '" + name.lexeme + "'.");
    }
}
