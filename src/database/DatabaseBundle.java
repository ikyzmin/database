package database;

import database.entities.Scheme;

import java.util.List;

public class DatabaseBundle {
    private List<Scheme> schemes;
    private boolean submitted;


    public DatabaseBundle putSchemes(List<Scheme> schemes) {
        this.schemes = schemes;
        return this;
    }

    public DatabaseBundle submit() throws UnsupportedOperationException {
        if (schemes != null) {
            submitted = true;
            return this;
        } else {
            throw new UnsupportedOperationException(createMessage());
        }
    }

    private String createMessage(){
        StringBuilder message = new StringBuilder("You need to add");
        message.append(schemes == null ? " schemes" : " Unsupported operation")
                .append(" to this builder");
        return message.toString();
    }

    public List<Scheme> getSchemes() throws UnsupportedOperationException {
        if (!submitted){
            throw new UnsupportedOperationException(createMessage());
        }
        return schemes;
    }


    public boolean isSubmitted(){
        return submitted;
    }
}
