package com.example.testandologoali;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.patinho.logoali.R;
import com.example.testandologoali.db.BancoDeDadosTeste;
import com.example.testandologoali.db.Usuario;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

public class GPlusActivity extends AppCompatActivity implements
        View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess;
    private LinearLayout llProfileLayout;
    private ImageView imgProfilePic;
    private TextView txtName, txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignOut = findViewById(R.id.btn_sign_out);
        btnRevokeAccess = findViewById(R.id.btn_revoke_access);
        llProfileLayout = findViewById(R.id.llProfile);
        imgProfilePic = findViewById(R.id.imgProfilePic);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);

        btnSignIn.setOnClickListener(this);
        btnSignOut.setOnClickListener(this);
        btnRevokeAccess.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                status -> updateUI(false));
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                status -> updateUI(false));
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
//            Uri photoUrl = acct.getPhotoUrl();
//            String personPhotoUrl = photoUrl == null ? "" : acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            BancoDeDadosTeste.getInstance().selectAdministradorByEmail(email, result1 -> {

                //Se usuário não existe, criar
                if (result1.getSingleObject() == null) {
                    createUserDialog(personName, email);
                } else {
                    logIn((Usuario) result1.getSingleObject());
                }
            });

        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);

        }

    }

    private void createUserDialog(String name, String email) {
        Usuario user = new Usuario();
        user.setNome(name);
        user.setEmail(email);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Você é um cliente ou dono de estabelecimento?")
                .setTitle("Bem vindo!")
                .setPositiveButton("Cliente", (dialog, which) -> {
                            user.setAcesso(BancoDeDadosTeste.USER);
                            createUserOnBackend(user);
                        }
                )
                .setNegativeButton("Dono", (dialog, which) -> {
                    user.setAcesso(BancoDeDadosTeste.ADMIN);
                    createUserOnBackend(user);
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createUserOnBackend(Usuario user) {
        BancoDeDadosTeste.getInstance(null).insertUsuario(user, result -> BancoDeDadosTeste.getInstance().selectAdministradorByEmail(user.getEmail(), result1 -> logIn((Usuario) result1.getSingleObject())));
    }


    private void logIn(Usuario usuario) {
        LoginHandler.setUsuario(usuario);

        Intent intent = null;

        if (LoginHandler.getUsuario() != null) {
            switch (LoginHandler.getUsuario().getAcesso()) {
                case BancoDeDadosTeste.USER:
                    //Se Role é USER, ir à tela de pesquisa
                    intent = new Intent(GPlusActivity.this, MainActivity.class);
                    break;
                case BancoDeDadosTeste.ADMIN:
                    //Se Role é Admin, ir à tela de Meus Estabelecimentos
                    intent = new Intent(GPlusActivity.this, ActivityEstabelecimentos.class);
                    break;
            }
            startActivity(intent);

            if (mProgressDialog != null)
                mProgressDialog.dismiss();

            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_sign_in:
                signIn();
                break;

            case R.id.btn_sign_out:
                signOut();
                break;

            case R.id.btn_revoke_access:
                revokeAccess();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the USER's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the USER has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the USER silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(googleSignInResult -> {
                hideProgressDialog();
                handleSignInResult(googleSignInResult);
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            btnSignIn.setVisibility(View.GONE);
            btnSignOut.setVisibility(View.VISIBLE);
            btnRevokeAccess.setVisibility(View.VISIBLE);
            llProfileLayout.setVisibility(View.VISIBLE);
        } else {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignOut.setVisibility(View.GONE);
            btnRevokeAccess.setVisibility(View.GONE);
            llProfileLayout.setVisibility(View.GONE);
        }
    }
}