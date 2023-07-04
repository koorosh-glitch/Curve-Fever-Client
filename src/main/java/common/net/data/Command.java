package common.net.data;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class Command implements Serializable, Runnable {
    public final Map headers = new HashMap<>();
    @Getter
    protected transient Entity recipient;

    public Command (Entity recipient) {
        this.recipient = recipient;
    }

    public Command(Command original, Entity recipient) {
        headers.putAll(original.headers);
        this.recipient = recipient;
    }


    public Command addHeader(Object k, Object v) {
        headers.put(k, v);
        return this;
    }

    public Object getHeader(Object key) {
        return headers.get(key);
    }

    public boolean isValid(Command command) {return true;};

    @Override
    public void run() {

    }
}
