package gr.auth.csd.taskmanager;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MainActivity extends ActionBarActivity
                          implements MemoryInfoFragment.OnFragmentInteractionListener,
                                     DeviceInfoFragment.OnFragmentInteractionListener,
                                     ProcessesFragment.OnFragmentInteractionListener{

    private HashMap<String,Fragment> fragments;
    final static HashMap<String, Integer> positioning;

    static {
        positioning = new HashMap<>();
        positioning.put("Memory Stats", 0);
        positioning.put("Processes", 1);
        positioning.put("Device Info", 2);
    }

    public MainActivity()
    {
        super();
        fragments = new HashMap<>();
        MemoryInfoFragment memoryInfoFragment = new MemoryInfoFragment();
        DeviceInfoFragment deviceInfoFragment = new DeviceInfoFragment();
        ProcessesFragment processesFragment = new ProcessesFragment();
        fragments.put("Memory Stats", memoryInfoFragment);
        fragments.put("Processes", processesFragment);
        fragments.put("Device Info", deviceInfoFragment);
    }
    private ArrayList<Map<String, Object>> buildData(HashMap<String,Fragment> deviceInfoData)
    {
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<positioning.size();++i) {
            list.add(null);
        }
        Set<Map.Entry<String,Fragment>> keyEntries = deviceInfoData.entrySet();
        for(Map.Entry<String,Fragment> e : keyEntries)
        {
            list.set(positioning.get(e.getKey()), putData(e.getKey(), e.getValue()));
        }
        return list;
    }

    private HashMap<String,Object> putData (String item, Fragment fragment)
    {
        HashMap<String, Object> itemM = new HashMap<>();
        itemM.put("item",item);
        itemM.put("subitem",fragment);
        return itemM;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ListView menuOptions = (ListView) this.findViewById(R.id.left_drawer);
        String[] from = {"item"};
        ArrayList<Map<String,Object>> list = buildData(this.fragments);

        SimpleAdapter adapter = new SimpleAdapter(this.getApplicationContext()
                , list, android.R.layout.simple_list_item_2
                , from ,new int[]{android.R.id.text1});
        menuOptions.setAdapter(adapter);
        menuOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selection = "";
                Set<Map.Entry<String, Integer>> entries = positioning.entrySet();
                for (Map.Entry<String, Integer> entry : entries) {
                    if (entry.getValue() == position) {
                        selection = entry.getKey();
                        break;
                    }
                }
                Fragment fragmentToBeCommitted = fragments.get(selection);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragmentToBeCommitted);
                fragmentTransaction.commit();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    android.support.v7.app.ActionBar ab = getSupportActionBar();
                    ab.setSubtitle(selection);
                }
                DrawerLayout theDrawer = (DrawerLayout)findViewById(R.id.drawer_layout);
                theDrawer.closeDrawers();
            }
        });

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
        fragmentTransaction.replace(R.id.content_frame, deviceInfoFragment);
        fragmentTransaction.commit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setSubtitle("Device Info");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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
