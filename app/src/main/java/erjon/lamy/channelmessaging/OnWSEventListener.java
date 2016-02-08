package erjon.lamy.channelmessaging;

/**
 * Created by Erjon on 02/02/2016.
 */
public interface OnWSEventListener {
    public void OnSuccess(String result);

    public void OnError();
}
