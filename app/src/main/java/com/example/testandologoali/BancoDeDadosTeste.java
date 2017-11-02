package com.example.testandologoali;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.patinho.logoali.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

class BancoDeDadosTeste {

    /**
     * Mobile Service Client reference
     */
    private MobileServiceClient mClient;

    /**
     * Mobile Service Table used to access data
     */
    private MobileServiceTable<ToDoItem> mToDoTable;

    private static ArrayList<Estabelecimento> estabelecimentos = new ArrayList<>();
    private static ArrayList<Fidelidade> fidelidades = new ArrayList<>();

    private static Usuario[] usuarios = new Usuario[]{
            new Usuario(0, "João", "Paulo", "25864592514", "jaopaulo@gmail.com", "minha15senha", Usuario.Role.ADMIN),
            new Usuario(1, "Pedro", "Ramos", "25419988800", "pramos@gmail.com", "msdha", Usuario.Role.USER),
            new Usuario(2, "Felipe", "Coelho", "37058869590", "dasdas@gmail.com", "suecas449", Usuario.Role.ADMIN),
            new Usuario(3, "Victor", "Vasconcellos", "45512165890", "victorpadovani94@gmail.com", "p", Usuario.Role.ADMIN),
            new Usuario(4, "Renata", "Guimarães", "35500026556", "renatinha_lolo@gmail.com", "meninassuperpoderosas", Usuario.Role.USER),
            new Usuario(5, "Tomas", "Turbando", "15122237840", "tomastur@gmail.com", "tomesmo", Usuario.Role.USER),
            new Usuario(6, "Bianca", "Preto", "15199222306", "biamar@gmail.com", "6738894", Usuario.Role.USER),
            new Usuario(7, "Fernanda", "Rubião", "27789669500", "fea055@gmail.com", "458448748as", Usuario.Role.USER),
            new Usuario(8, "Dener", "Desmond", "16497585420", "dener.siros@gmail.com", "acS269", Usuario.Role.ADMIN),
            new Usuario(9, "Rodrigo", "Salles", "98908754577", "rsalles@gmail.com", "975791", Usuario.Role.ADMIN),
            new Usuario(10, "Usuario", "Teste", "00000000000", "u", "t", Usuario.Role.USER),
            new Usuario(11, "Suporte", "Usuario", "35845695864", "s", "p", Usuario.Role.SUPPORT),
    };

    static {
        estabelecimentos.add(new Estabelecimento(0, "Corte Rápido", "Rua México", "523", "Vista Verde", "São José dos Campos", "38655545", "Corte Masculino R$20,00\nCorte Feminino R$35,00", "9 horas - 22 horas", 0, 5f, R.drawable.barbearia1, R.drawable.barbearia1_thumb));
        estabelecimentos.add(new Estabelecimento(1, "Corte Bom", "Rua Tubarão", "88", "Aquários", "São José dos Campos", "39900258", "Aplicação de Tinta R$50,00\nLavagem R$30,00", "9 horas - 21 horas", 1, 4.5f, R.drawable.barbearia2, R.drawable.barbearia2_thumb));
        estabelecimentos.add(new Estabelecimento(2, "Corte Lindo", "Rua Parafuso", "994", "Jardim das industrias", "São José dos Campos", "33590663", "Luzes com Matização R$150,00\nTratamento Capilar a Lazer R$500,00\nLuzes Papel com Matização R$250,00", "7 horas - 22 horas", 2, 5f, R.drawable.barbearia1, R.drawable.barbearia1_thumb));
        estabelecimentos.add(new Estabelecimento(3, "Corte Prático", "Rua dos Macacos", "110", "Vila Madrid", "Caçapava", "33255222", "Alongamento de Cabelos R$385,00\nRetoque de Raiz R$100,00\nDesintoxicação de Cabelos R$380,00\nCorte Feminino R$60,00\nBordados Laces R$300,00", "8 horas - 23 horas", 3, 4f, R.drawable.barbearia2, R.drawable.barbearia2_thumb));
        estabelecimentos.add(new Estabelecimento(4, "Corte Limpo", "Avenida dos Limões", "2056", "Vale das Frutas", "Caçapava", "35459900", "Hidratação R$50,00", "6 horas - 18 horas", 4, 5f, R.drawable.barbearia1, R.drawable.barbearia1_thumb));
        estabelecimentos.add(new Estabelecimento(5, "Cabelo Bom", "Avenida São José", "415", "Marlene Miranda", "Pindamonhangaba", "42218988", "Corte Feminino R$75,00\nCorte Maculino R$35,00\nEscova Modeladora R$80,00", "9 horas - 20 horas", 5, 4.5f, R.drawable.barbearia2, R.drawable.barbearia2_thumb));
        estabelecimentos.add(new Estabelecimento(6, "Cabelo Pronto", "Praça das bolhas", "80", "Centro", "Pindamonhangaba", "33212122", "Luzes Retoque R$150,00\nEscova Progressiva R$250,00\nCorte Masculino R$45,00", "8 horas - 20 horas", 6, 3f, R.drawable.barbearia1, R.drawable.barbearia1_thumb));
        estabelecimentos.add(new Estabelecimento(7, "Cabelo Colorido", "Rua da Petunia", "784", "Jardim das Flores", "Pindamonhangaba", "34445496", "Regeneração Capilar R$80,00\nReconstrução Capilar R$200,00", "7 horas - 21 horas", 7, 4.74f, R.drawable.barbearia2, R.drawable.barbearia2_thumb));
        estabelecimentos.add(new Estabelecimento(8, "Cabelo no Chão", "Rua do sogro", "69", "Cunha", "Jacareí", "32246996", "Escova R$20,00\nCorte Masculino Desenhado R$30,00\nColoração R$120,00", "6 horas - 19 horas", 8, 5f, R.drawable.barbearia1, R.drawable.barbearia1_thumb));
    }

    private static final Object lock = new Object();
    private static BancoDeDadosTeste instance = null;

    public static BancoDeDadosTeste getInstance(Activity activity) {
        synchronized (lock) {
            if (instance == null) {
                instance = new BancoDeDadosTeste(activity);
            }
        }
        return instance;
    }

    private BancoDeDadosTeste(Activity activity) {
        initializeConnection(activity);
    }

    private void initializeConnection (Activity activity) {
        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mClient = new MobileServiceClient(
                    "https://testandologoali.azurewebsites.net",
                    activity);

            // Extend timeout from default of 10s to 20s
            mClient.setAndroidHttpClientFactory(() -> {
                OkHttpClient client = new OkHttpClient();
                client.setReadTimeout(20, TimeUnit.SECONDS);
                client.setWriteTimeout(20, TimeUnit.SECONDS);
                return client;
            });

            // Get the Mobile Service Table instance to use

//            mToDoTable = mClient.getTable(ToDoItem.class);

            // Offline Sync
            //mToDoTable = mClient.getSyncTable("ToDoItem", ToDoItem.class);

            //Init local storage
            initLocalStore().get();

        } catch (Exception e) {
            Log.e(BancoDeDadosTeste.class.getName(), "Failed to initialize database connection", e);
        }
    }

    //TODO
    public static ArrayList<Estabelecimento> selectEstabelecimentoByCidade(String cidade) {
        ArrayList<Estabelecimento> arrayListEstabelecimento = new ArrayList<>();
        if (cidade.isEmpty()) {
            return arrayListEstabelecimento;
        }
        for (Estabelecimento e : estabelecimentos) {
            //Gambiarra para ignorar maiúsculas
            if (e.getmCidadeDoEstabelecimento().toUpperCase().contains(cidade.toUpperCase())) {
                arrayListEstabelecimento.add(e);
            }
        }
        return arrayListEstabelecimento;
    }

    //TODO
    public static ArrayList<Estabelecimento> selectEstabelecimentoByAdmin(int userID) {
        ArrayList<Estabelecimento> arrayListEstabelecimento = new ArrayList<>();
        for (Estabelecimento e : estabelecimentos) {
            if (e.getmIdAdministrador() == userID) {
                arrayListEstabelecimento.add(e);
            }
        }
        return arrayListEstabelecimento;
    }

    //TODO
    public static Estabelecimento selectEstabelecimento(int id) {
        for (Estabelecimento e : estabelecimentos) {
            if (e.getmId() == id) {
                return e;
            }
        }
        return null;
    }

    //TODO
    public static Usuario selectAdministrador(int id) {
        for (Usuario a : usuarios) {
            if (a.getmIdUsuario() == id) {
                return a;
            }
        }
        return null;
    }

    //TODO
    public static Usuario selectAdministradorByEmail(String email) {
        for (Usuario a : usuarios) {
            if (a.getmEmail().equalsIgnoreCase(email)) {
                return a;
            }
        }
        return null;
    }

    /**
     * //TODO
     * @param email
     * @return Estrutura com o resultado da autenticacao
     */
    public static AuthenticateUserReturn authenticateUser(String email) {
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

    //TODO
    public static Estabelecimento updateEstabelecimento(Estabelecimento in) {
        Estabelecimento estabelecimento = selectEstabelecimento(in.getmId());
        if (estabelecimento != null && LoginHandler.getUsuario().getmIdUsuario() == estabelecimento.getmIdAdministrador()) {
            estabelecimento = in;
            return estabelecimento;
        }
        return null;
    }

    //TODO
    public static Estabelecimento createEstabelecimento(Estabelecimento in) {
//        if (!LoginHandler.getUsuario().getmRole().equals(Usuario.Role.SUPPORT)) {
//            return null;
//        }
        assert in != null;
        Estabelecimento newEst = new Estabelecimento(getNextID(),
                in.getmNomeDoEstabelecimento(),
                in.getmRuaDoEstabelecimento(),
                in.getmNumeroDoEstabelecimento(),
                in.getmBairroDoEstabelecimento(),
                in.getmCidadeDoEstabelecimento(),
                in.getmTelefoneDoEstabelecimento(),
                in.getmServicos(),
                in.getmHorarioAtendimento(),
                in.getmIdAdministrador(),
                in.getmNotaEstabelecimento(),
                in.getmImagemEstabelecimento(),
                in.getmImagemEstabelecimentoThumb());
        estabelecimentos.add(newEst);
        return newEst;
    }

    //TODO
    public static void addFidelidade(int idUsuario, int idEstabelecimento) {
        Estabelecimento estabelecimento = selectEstabelecimento(idEstabelecimento);
        if (LoginHandler.getUsuario().getmIdUsuario() != estabelecimento.getmIdAdministrador()) {
            return;
        }
        int count = countFidelidade(idUsuario, idEstabelecimento);
        if (count == 0) {
            fidelidades.add(new Fidelidade(idUsuario,idEstabelecimento));
        } else {
            selectFidelidade(idUsuario,idEstabelecimento).addContagem();
        }
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

    //TODO
    private static int getNextID() {
        int ret = -1;
        for (Estabelecimento e : estabelecimentos) {
            if (e.getmId() > ret) {
                ret = e.getmId();
            }
        }
        return ++ret;
    }

    private static class LocalStoreAsyncTask extends AsyncTask<Void, Void, Void> {
        private final MobileServiceClient mClient;

        LocalStoreAsyncTask(MobileServiceClient mClient) {
            this.mClient = mClient;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                MobileServiceSyncContext syncContext = mClient.getSyncContext();

                if (syncContext.isInitialized())
                    return null;

                SQLiteLocalStore localStore = new SQLiteLocalStore(mClient.getContext(), "OfflineStore", null, 1);

                Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                tableDefinition.put("id", ColumnDataType.String);
                tableDefinition.put("text", ColumnDataType.String);
                tableDefinition.put("complete", ColumnDataType.Boolean);

                localStore.defineTable("ToDoItem", tableDefinition);

                SimpleSyncHandler handler = new SimpleSyncHandler();

                syncContext.initialize(localStore, handler).get();

            } catch (final Exception e) {
                Log.e(LocalStoreAsyncTask.class.getName(), "", e);
            }

            return null;
        }
    }

    /**
     * Initialize local storage
     * @return
     * @throws MobileServiceLocalStoreException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new LocalStoreAsyncTask(mClient) ;

        return runAsyncTask(task);
    }

    /**
     * Run an ASync task on the corresponding executor
     * @param task
     * @return
     */
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}