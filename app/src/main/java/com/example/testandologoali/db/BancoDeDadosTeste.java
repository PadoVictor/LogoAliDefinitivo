package com.example.testandologoali.db;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class BancoDeDadosTeste {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    private MobileServiceTable<Usuario> mTabelaUsuario;
    private MobileServiceTable<Estabelecimentos> mTabelaEstab;
    private MobileServiceTable<Fidelidade> mTabelaFidelidade;
    private MobileServiceTable<Nota> mTabelaNota;

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
            /*
      Mobile Service Client reference
     */
            MobileServiceClient mClient = new MobileServiceClient(
                    "http://logoalitcc.azurewebsites.net/",
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
            mTabelaFidelidade = mClient.getTable(Fidelidade.class);
            mTabelaNota = mClient.getTable(Nota.class);

        } catch (Exception e) {
            Log.e(BancoDeDadosTeste.class.getName(), "Failed to initialize database connection", e);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void selectEstabelecimentoByCidade(String cidade, BancoDeDadosTesteListener listener) {
        logQuery("selectEstabelecimentoByCidade", cidade);
        new QueryTask<Estabelecimentos>(listener) {
            @Override
            List<Estabelecimentos> query() throws InterruptedException, ExecutionException {
                return mTabelaEstab.where().field("cidade").eq(val(cidade)).execute().get();
//                return mTabelaEstab.where().subStringOf(cidade, "cidade").execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void selectEstabelecimentoByAdmin(String userID, BancoDeDadosTesteListener listener) {
        logQuery("selectEstabelecimentoByAdmin", userID);
        new QueryTask<Estabelecimentos>(listener) {
            @Override
            List<Estabelecimentos> query() throws InterruptedException, ExecutionException {
                return mTabelaEstab.where().field("idadministrador").eq(val(userID)).execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void selectEstabelecimento(String id, BancoDeDadosTesteListener listener) {
        logQuery("selectEstabelecimento", id);
        new QueryTask<Estabelecimentos>(listener) {
            @Override
            List<Estabelecimentos> query() throws InterruptedException, ExecutionException {
                assertUnique = true;
                return mTabelaEstab.where().field("id").eq(val(id)).execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void selectCidades(BancoDeDadosTesteListener listener) {
        logQuery("selectCidades");
        new QueryTask<Estabelecimentos>(listener) {
            @Override
            List<Estabelecimentos> query() throws InterruptedException, ExecutionException {
                return mTabelaEstab.where().select("cidade").execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void selectAdministrador(String id, BancoDeDadosTesteListener listener) {
        logQuery("selectAdministrador", id);
        new QueryTask<Usuario>(listener) {
            @Override
            List<Usuario> query() throws InterruptedException, ExecutionException {
                assertUnique = true;
                return mTabelaUsuario.where().field("id").eq(val(id)).execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void selectAdministradorByEmail(String email, BancoDeDadosTesteListener listener) {
        logQuery("selectAdministradorByEmail", email);
        new QueryTask<Usuario>(listener) {
            @Override
            List<Usuario> query() throws InterruptedException, ExecutionException {
                assertUnique = true;
                return mTabelaUsuario.where().field("email").eq(val(email)).execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertUsuario(Usuario usuario, BancoDeDadosTesteListener listener) {
        logQuery("insertUsuario", usuario);
//        if (!LoginHandler.getUsuario().getAcesso().equals(Usuario.ADMIN)) {
//            return null;
//        }
        new QueryTask<Usuario>(listener) {
            @Override
            List<Usuario> query() throws InterruptedException, ExecutionException {
                return toList(mTabelaUsuario.insert(usuario).get());
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void insertEstabelecimento(Estabelecimentos estabelecimento, BancoDeDadosTesteListener listener) {
        logQuery("updateEstabelecimento", estabelecimento);
        new QueryTask<Estabelecimentos>(listener) {
            @Override
            List<Estabelecimentos> query() throws InterruptedException, ExecutionException {
                return toList(mTabelaEstab.insert(estabelecimento).get());
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void updateEstabelecimento(Estabelecimentos in, BancoDeDadosTesteListener listener) {
        logQuery("updateEstabelecimento", in);
        new QueryTask<Estabelecimentos>(listener) {
            @Override
            List<Estabelecimentos> query() throws InterruptedException, ExecutionException {
                return toList(mTabelaEstab.update(in).get());
            }
        }.execute();
    }

    //TODO
    @SuppressLint("StaticFieldLeak")
    public void addFidelidade(Fidelidade fidelidade, BancoDeDadosTesteListener listener) {
        logQuery("addFidelidade", fidelidade);
        new QueryTask<Fidelidade>(listener) {
            @Override
            List<Fidelidade> query() throws InterruptedException, ExecutionException {
                fidelidade.addContagem();
                return toList(mTabelaFidelidade.update(fidelidade).get());
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void createFidelidade(Fidelidade fidelidade, BancoDeDadosTesteListener listener) {
        logQuery("createFidelidade", fidelidade);
        new QueryTask<Fidelidade>(listener) {
            @Override
            List<Fidelidade> query() throws InterruptedException, ExecutionException {
                return toList(mTabelaFidelidade.insert(fidelidade).get());
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void getFidelidade(String idUsuario, String idEstabelecimento, BancoDeDadosTesteListener listener) {
        logQuery("getFidelidade", idUsuario, idEstabelecimento);
        new QueryTask<Fidelidade>(listener) {
            @Override
            List<Fidelidade> query() throws InterruptedException, ExecutionException {
                assertUnique = true;
                return mTabelaFidelidade
                        .where()
                        .field("idestabelecimento")
                        .eq(val(idEstabelecimento))
                        .and()
                        .field("idusuario")
                        .eq(val(idUsuario)).execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void selectFidelidadeByUsuario(String idUsuario, BancoDeDadosTesteListener listener) {
        logQuery("selectFidelidadeByUsuario", idUsuario);
        new QueryTask<Fidelidade>(listener) {
            @Override
            List<Fidelidade> query() throws InterruptedException, ExecutionException {
                return mTabelaFidelidade
                        .where()
                        .field("idusuario")
                        .eq(val(idUsuario)).execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void getNota(String idUsuario, String idEstabelecimento, BancoDeDadosTesteListener listener) {
        logQuery("getNota", idUsuario, idEstabelecimento);
        new QueryTask<Nota>(listener) {
            @Override
            List<Nota> query() throws InterruptedException, ExecutionException {
                assertUnique = true;
                return mTabelaNota
                        .where()
                        .field("idestabelecimento")
                        .eq(val(idEstabelecimento))
                        .and()
                        .field("idcliente")
                        .eq(val(idUsuario)).execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void getNotasDoEstabelecimento(String idEstabelecimento, BancoDeDadosTesteListener listener) {
        logQuery("getNotasDoEstabelecimento", idEstabelecimento);
        new QueryTask<Nota>(listener) {
            @Override
            List<Nota> query() throws InterruptedException, ExecutionException {
                return mTabelaNota
                        .where()
                        .field("idestabelecimento")
                        .eq(val(idEstabelecimento)).execute().get();
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void createOrUpdateNota(Nota nota, BancoDeDadosTesteListener listener) {
        logQuery("createOrUpdateNota", nota);
        BancoDeDadosTeste.getInstance().getNota(nota.getIdUsuario(), nota.getIdEstabelecimento(), result -> {
            Nota nota1 = (Nota) result.getSingleObject();

            if (nota1 == null) {
                new QueryTask<Nota>(listener) {
                    @Override
                    List<Nota> query() throws InterruptedException, ExecutionException {
                        return toList(mTabelaNota.insert(nota).get());
                    }
                }.execute();
            } else {
                new QueryTask<Nota>(listener) {
                    @Override
                    List<Nota> query() throws InterruptedException, ExecutionException {
                        return toList(mTabelaNota.update(nota).get());
                    }
                }.execute();
            }
        });
    }

    // Fim das queries

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

    private void logQuery(String methodName, String... params) {
        Log.d(this.getClass().getName(), "Calling " + methodName + " with params " + Arrays.toString(params));
    }

    private void logQuery(String methodName, Object param) {
        Log.d(this.getClass().getName(), "Calling " + methodName + " with params " + param.toString());
    }


    public abstract static class QueryTask<T> extends AsyncTask<Void, Void, QueryResult<T>> {
        private final BancoDeDadosTesteListener<T> listener;

        boolean assertUnique = false;

        QueryTask(BancoDeDadosTesteListener<T> listener) {
            this.listener = listener;
        }

        void execute() {
            super.execute();
        }

        @Override
        protected QueryResult<T> doInBackground(Void... voids) {
            try {
                QueryResult<T> result = new QueryResult<>(true, query());
                if (!assertUnique || result.isUnique()) {
                    Log.d(this.getClass().getName(), "Query returned: " + result.getObjectsList().toString());
                    return result;
                }
                throw new InternalError("Query result is not unique! " + result.getObjectsList().toString());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return new QueryResult<>(false, null);
        }

        abstract List<T> query() throws InterruptedException, ExecutionException;

        List<T> toList(T result) {
            ArrayList<T> resultList = new ArrayList<>();
            resultList.add(result);
            return resultList;
        }

        @Override
        protected void onPostExecute(QueryResult<T> tQueryResult) {
            if (!tQueryResult.isSuccess) {
                Log.e(this.getClass().getName(), "Database Error");
            }
            listener.onQueryResult(tQueryResult);
        }
    }

    public interface BancoDeDadosTesteListener<T> {
        void onQueryResult(QueryResult<T> result);
    }

    public static class QueryResult<T> {
        private final boolean isSuccess;
        private final List<T> resultObjects;

        QueryResult(boolean isSuccess, List<T> resultObjects) {
            this.isSuccess = isSuccess;
            this.resultObjects = resultObjects;
        }

        public List<T> getObjectsList() {
            return resultObjects;
        }

        public T getSingleObject() {
            return (resultObjects == null || resultObjects.isEmpty()) ? null : resultObjects.get(0);
        }

        boolean isUnique() {
            return resultObjects != null && resultObjects.size() <= 1;
        }
    }
}