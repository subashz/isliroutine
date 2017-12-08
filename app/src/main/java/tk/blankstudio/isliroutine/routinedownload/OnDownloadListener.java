package tk.blankstudio.isliroutine.routinedownload;

public interface OnDownloadListener {
    public void onStart();
    public void onRetry();
    public void onSuccessfull();
    public void onFailure(Throwable t);
    public void noInternet();
}
