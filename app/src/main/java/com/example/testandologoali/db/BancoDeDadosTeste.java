package com.example.testandologoali.db;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.testandologoali.LoginHandler;
import com.example.testandologoali.ToDoItem;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class BancoDeDadosTeste {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    private MobileServiceTable<Usuario> mTabelaUsuario;

    private MobileServiceTable<Estabelecimentos> mTabelaEstab;
    private static ArrayList<Fidelidade> fidelidades = new ArrayList<>();

    //    private static final Object lock = new Object();
    private static BancoDeDadosTeste instance = null;

    public static BancoDeDadosTeste getInstance(Activity activity) {
        if (instance == null) {
            instance = new BancoDeDadosTeste(activity);
        }
        return instance;
    }

    public static BancoDeDadosTeste getInstance() {
        assert instance != null;
        return instance;
    }

    private BancoDeDadosTeste(Activity activity) {
        initializeConnection(activity);
    }

    private void initializeConnection(Activity activity) {
        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://testandologoali.azurewebsites.net",
                    activity).withFilter(new ProgressFilter());

            // Extend timeout from default of 10s to 20s
            mClient.setAndroidHttpClientFactory(() -> {
                OkHttpClient client = new OkHttpClient();
                client.setReadTimeout(10, TimeUnit.SECONDS);
                client.setWriteTimeout(10, TimeUnit.SECONDS);
                return client;
            });

            // Get the Mobile Service Table instance to use
            mTabelaEstab = mClient.getTable(Estabelecimentos.class);
            mTabelaUsuario = mClient.getTable(Usuario.class);

        } catch (Exception e) {
            Log.e(BancoDeDadosTeste.class.getName(), "Failed to initialize database connection", e);
        }
    }

    public List<Estabelecimentos> selectEstabelecimentoByCidade(String cidade) {
        List<Estabelecimentos> estabelecimentos = new ArrayList<>();
        try {
            estabelecimentos = mTabelaEstab.where().subStringOf(cidade, "cidade").execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return estabelecimentos;
    }

    public List<Estabelecimentos> selectEstabelecimentoByAdmin(String userID) {
        List<Estabelecimentos> estabelecimentos = new ArrayList<>();
        try {
            estabelecimentos = mTabelaEstab.where().field("idadministrador").eq(val(userID)).execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return estabelecimentos;
    }

    public Estabelecimentos selectEstabelecimento(String id) {
        try {
            List<Estabelecimentos> estabelecimentos = mTabelaEstab.where().field("id").
                    eq(val(id)).execute().get();
            if (estabelecimentos.size() == 1) {
                return estabelecimentos.get(0);
            } else if (estabelecimentos.size() > 0) {
                Log.e(BancoDeDadosTeste.class.getName(), "Estabelecimento id " + id + " is not unique!");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Estabelecimentos insertEstabelecimento(Estabelecimentos estabelecimento) throws ExecutionException, InterruptedException {
//        if (!LoginHandler.getUsuario().getAcesso().equals(Usuario.ADMIN)) {
//            return null;
//        }
//        synchronized (lock) {
        return mTabelaEstab.insert(estabelecimento).get();
//        }
    }

    public Usuario selectAdministrador(String id) {
        try {
            List<Usuario> usuarios = mTabelaUsuario.where().field("id").
                    eq(id).execute().get(20l, TimeUnit.SECONDS);
            if (usuarios.size() == 1) {
                return usuarios.get(0);
            } else if (usuarios.size() > 0) {
                Log.e(BancoDeDadosTeste.class.getName(), "User id " + id + " is not unique!");
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Usuario selectAdministradorByEmail(String email) {
        try {
            List<Usuario> usuarios = mTabelaUsuario.where().field("email").
                    eq(val(email)).execute().get(10l, TimeUnit.SECONDS);
            if (usuarios.size() == 1) {
                return usuarios.get(0);
            } else if (usuarios.size() > 0) {
                Log.e(BancoDeDadosTeste.class.getName(), "User email " + email + " is not unique!");
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Usuario insertUsuario(Usuario usuario) throws ExecutionException, InterruptedException {
//        if (!LoginHandler.getUsuario().getAcesso().equals(Usuario.ADMIN)) {
//            return null;
//        }
        return mTabelaUsuario.insert(usuario).get();

    }

    /**
     * //TODO
     *
     * @param email
     * @return Estrutura com o resultado da autenticacao
     */
    public AuthenticateUserReturn authenticateUser(String email) {
        Usuario user = selectAdministradorByEmail(email);

        if (user == null)
            return new AuthenticateUserReturn(-2, null);
        else
            return new AuthenticateUserReturn(1, user);
    }

    //TODO
    public static class AuthenticateUserReturn {
        static final String USER_ID = "com.example.patinho.logoali.BancoDeDadosTeste.AuthenticateUserReturn.USER_ID";
        /**
         * 1 = sucesso, -1 = senha incorreta, -2 = usuário inexistente
         */
        private int err;
        private Usuario usuario;

        /**
         * 1 = sucesso, -1 = senha incorreta, -2 = usuário inexistente
         */
        public AuthenticateUserReturn(int err, Usuario usuario) {
            this.err = err;
            this.usuario = usuario;
        }

        public int getErr() {
            return err;
        }

        public Usuario getUsuario() {
            return usuario;
        }
    }

    public Estabelecimentos updateEstabelecimento(Estabelecimentos in) {
        Estabelecimentos estabelecimento = selectEstabelecimento(in.getmId());
        if (estabelecimento != null && Objects.equals(LoginHandler.getUsuario().getId(), estabelecimento.getmIdAdministrador())) {
            estabelecimento = in;
            return estabelecimento;
        }
        return null;
    }

    //TODO
    public static void addFidelidade(int idUsuario, int idEstabelecimento) {
//        Estabelecimentos estabelecimento = selectEstabelecimento(idEstabelecimento);
//        if (LoginHandler.getUsuario().getId() != estabelecimento.getmIdAdministrador()) {
//            return;
//        }
//        int count = countFidelidade(idUsuario, idEstabelecimento);
//        if (count == 0) {
//            fidelidades.add(new Fidelidade(idUsuario, idEstabelecimento));
//        } else {
//            selectFidelidade(idUsuario, idEstabelecimento).addContagem();
//        }
    }

    //TODO
    public static int countFidelidade(int idUsuario, int idEstabelecimento) {
        for (Fidelidade f : fidelidades) {
            if (f.getmIdUsuario() == idUsuario && f.getmIdEstabelecimento() == idEstabelecimento) {
                return f.getmContagem();
            }
        }
        return 0;
    }

    //TODO
    public static Fidelidade selectFidelidade(int idUsuario, int idEstabelecimento) {
        for (Fidelidade f : fidelidades) {
            if (f.getmIdUsuario() == idUsuario && f.getmIdEstabelecimento() == idEstabelecimento) {
                return f;
            }
        }
        return null;
    }

    /**
     * Run an ASync task on the corresponding executor
     *
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class ProgressFilter implements ServiceFilter {
        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {
            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onFailure(Throwable e) {
                    resultFuture.setException(e);
                }

                @Override
                public void onSuccess(ServiceFilterResponse response) {
                    resultFuture.set(response);
                }
            });

            return resultFuture;
        }
    }
}