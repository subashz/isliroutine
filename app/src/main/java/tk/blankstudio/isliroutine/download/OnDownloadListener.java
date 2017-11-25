package tk.blankstudio.isliroutine.download;

public interface OnDownloadListener {
    public void onStart();
    public void onRetry();
    public void onSuccessfull();
    public void onFailure(String errorTitle, String errorMessage);
    public void noInternet();
}
