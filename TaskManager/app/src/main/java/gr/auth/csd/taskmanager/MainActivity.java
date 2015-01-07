package gr.auth.csd.taskmanager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.text.ParseException;
import java.util.Date;


public class MainActivity extends ActionBarActivity
                          implements MemoryInfoFragment.OnFragmentInteractionListener ,
        DeviceInfoFragment.OnFragmentInteractionListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        long[] freeMemory = SystemQuery.freeMemory();
        long[] availableMemory = SystemQuery.totalMemory();
        Date today = new Date();

        try {
            DBHandler dbHandler = new DBHandler(this);
            Log.d("Insert today's stats", "Inserting");
            dbHandler.addRecord(new MemoryInfo(today, freeMemory[0], availableMemory[0],
                    freeMemory[1], availableMemory[1]));
            Log.d("getRecord", dbHandler.getRecord(today).toString());
        }
        catch (android.database.sqlite.SQLiteConstraintException e) {
            Log.d("DuplicateKey", "Duplicate entry for today");
        }
        catch (ParseException e){}

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        MemoryInfoFragment memoryInfoFragment = new MemoryInfoFragment();
//        fragmentTransaction.replace(android.R.id.content, memoryInfoFragment);
//        fragmentTransaction.commit();
        DeviceInfoFragment deviceInfoFragment = new DeviceInfoFragment();
        SystemQuery.getSystemMetrics(this.getApplicationContext(), deviceInfoFragment);
        fragmentTransaction.replace(android.R.id.content, deviceInfoFragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onFragmentInteraction(String id) {
//
//    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
