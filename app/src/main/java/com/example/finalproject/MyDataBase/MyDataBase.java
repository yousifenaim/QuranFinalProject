package com.example.finalproject.MyDataBase;

import android.content.Context;

        import androidx.annotation.NonNull;
import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;
        import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.finalproject.MyDataBase.Dao.AdvertisingDAO;
import com.example.finalproject.MyDataBase.Dao.BranchDAO;
import com.example.finalproject.MyDataBase.Dao.CenterDAO;
import com.example.finalproject.MyDataBase.Dao.CircleDAO;
import com.example.finalproject.MyDataBase.Dao.MahfazDAO;
import com.example.finalproject.MyDataBase.Dao.MySmsDAO;
import com.example.finalproject.MyDataBase.Dao.QuantityTypeDAO;
import com.example.finalproject.MyDataBase.Dao.StudentDAO;
import com.example.finalproject.MyDataBase.Dao.TaskDAO;
import com.example.finalproject.MyDataBase.Dao.UserDAO;
import com.example.finalproject.MyDataBase.Entity.Advertising;
import com.example.finalproject.MyDataBase.Entity.Branch;
import com.example.finalproject.MyDataBase.Entity.Center;
import com.example.finalproject.MyDataBase.Entity.Circle;
import com.example.finalproject.MyDataBase.Entity.Mahfaz;
import com.example.finalproject.MyDataBase.Entity.MySms;
import com.example.finalproject.MyDataBase.Entity.QuantityType;
import com.example.finalproject.MyDataBase.Entity.Student;
import com.example.finalproject.MyDataBase.Entity.Task;
import com.example.finalproject.MyDataBase.Entity.User;

import java.util.concurrent.ExecutorService;
        import java.util.concurrent.Executors;

@Database(entities = {User.class,Branch.class, Center.class, Circle.class, Mahfaz.class, Student.class, QuantityType.class, Task.class, Advertising.class, MySms.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract CenterDAO centerDAO();
    public abstract CircleDAO circleDAO();
    public abstract BranchDAO branchDAO();
    public abstract MahfazDAO mahfazDAO();
    public abstract StudentDAO studentDAO();
    public abstract QuantityTypeDAO quantityTypeDAO();
    public abstract TaskDAO taskDAO();
    public abstract AdvertisingDAO advertisingDAO();
    public abstract MySmsDAO mySmsDAO();

    private static volatile MyDataBase INSTANCE;
    private static final int NUMBER_OF_THREADS = 12;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MyDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDataBase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {

               BranchDAO branchDAO =INSTANCE.branchDAO();
               QuantityTypeDAO quantityType=INSTANCE.quantityTypeDAO();

                Branch branch =new Branch(1,"شمال غزة");
                Branch branch1 =new Branch(2,"غزة المدينة");
                Branch branch2 =new Branch(3,"الوسطى");
                Branch branch3 =new Branch(4,"خانيونس");
                Branch branch4 =new Branch(5,"رفح");

                branchDAO.insertBranch(branch);
                branchDAO.insertBranch(branch1);
                branchDAO.insertBranch(branch2);
                branchDAO.insertBranch(branch3);
                branchDAO.insertBranch(branch4);


                QuantityType quantityType1 =new QuantityType(1,"اية");
                QuantityType quantityType2 =new QuantityType(2,"صفحة");
                QuantityType quantityType3 =new QuantityType(3,"سورة");
                QuantityType quantityType4 =new QuantityType(4,"جزء");

                quantityType.insertQuantityType(quantityType1,quantityType2,quantityType3,quantityType4);

            });
        }
    };
}