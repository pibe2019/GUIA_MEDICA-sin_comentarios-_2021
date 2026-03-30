package guiasalud.estableciemtos.guiamedica.ui.AdminEstablecimiento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.jetbrains.annotations.NotNull;

import guiasalud.estableciemtos.guiamedica.LoadingDialog;
import guiasalud.estableciemtos.guiamedica.R;
import guiasalud.estableciemtos.guiamedica.verificacionInternet;
import static android.os.Build.VERSION_CODES.N;
import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;

public class webAdminFragment extends Fragment {
    private WebView web;
    private Context context;
    private verificacionInternet aInternt;
    public webAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context=getContext();
        aInternt=new verificacionInternet(context);

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!aInternt.HayInternet()) mostrarDialogInternet();
        else mostrarDialogAviso();
        return inflater.inflate(R.layout.fragment_web_admin, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        final LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.startLoadingDialog();
        //progressDialog=new ProgressDialog(context);
        //progressDialog.setMessage(context.getString(R.string.cargar));
        web=view.findViewById(R.id.webViewAdminEstablecimiento);
        String webUrl = "https://guiamedica.xyz/accesoUsuarioWeb.php";
        web.loadUrl(webUrl);
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());//String url
                return true;
            }
        });
       //web.setWebContentsDebuggingEnabled(false);
        if (savedInstanceState!=null){
            web.restoreState(savedInstanceState);
        }else {
            WebSettings ws=web.getSettings();
            ws.setJavaScriptEnabled(true);
            ws.setDomStorageEnabled(true);//activa las variables localHost para q la acepte el webView
            ws.setBuiltInZoomControls(true);//zoon del toush
            ws.setLoadsImagesAutomatically(true);
            ws.setBlockNetworkImage(false);//en true bloquea las imagenes
            //ws.getAllowContentAccess();
            ws.setAllowFileAccess(true);

            /*
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
                ws.setAllowFileAccessFromFileURLs(true);//no es necesario
                ws.setAllowUniversalAccessFromFileURLs(true);//no es necesario
            }*/
            ws.setAllowContentAccess(true);
            if (Build.VERSION.SDK_INT >= N) {
                ws.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);
                CookieManager.getInstance().setAcceptThirdPartyCookies(web, true);
            }
        }

        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //progressDialog.show();
                if (newProgress==100){
                    if(getActivity()!=null)
                    getActivity().setTitle(view.getTitle());
                    //progressDialog.dismiss();
                    loadingDialog.dismissDialog();
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        //web.getSettings().setJavaScriptEnabled(true);
        //web.zoomIn();
        //web.zoomOut();
    }

   @Override
    public void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        web.saveState(outState);
    }

    private void mostrarDialogInternet() {//dialogo basico
        new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_general)
                .setCancelable(false)
                .setMessage(R.string.dialog_btnflecha_internet_no)
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss()).show();
    }
    private void mostrarDialogAviso() {//dialogo basico
        new AlertDialog.Builder(context)
                .setTitle(R.string.dialog_webView_important)
                .setCancelable(false)
                .setMessage(R.string.dialog_webView)
                .setPositiveButton("Aceptar", (dialog, which) -> dialog.dismiss()).show();
    }


}
