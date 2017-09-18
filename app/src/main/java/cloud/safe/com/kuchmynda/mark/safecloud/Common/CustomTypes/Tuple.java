package cloud.safe.com.kuchmynda.mark.safecloud.Common.CustomTypes;

/**
 * Created by Markiian Kuchmynda on 18.09.2017.
 */

public class Tuple<F,S> {
    private F key;
    private S value;

    public Tuple(F key,S value){
        setKey(key);
        setValue(value);
    }
    public F getKey() {
        return key;
    }

    public void setKey(F key) {
        this.key = key;
    }

    public S getValue() {
        return value;
    }

    public void setValue(S value) {
        this.value = value;
    }
}
