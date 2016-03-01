package erjon.lamy.channelmessaging;

/**
 * Created by Erjon on 02/02/2016.
 */
public interface OnWSEventListener {
    public void OnSuccess(int requestCode, String result);

    public void OnError(int requestCode);
}
