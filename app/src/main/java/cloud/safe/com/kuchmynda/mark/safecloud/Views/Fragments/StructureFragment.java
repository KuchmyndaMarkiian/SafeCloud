package cloud.safe.com.kuchmynda.mark.safecloud.Views.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cloud.safe.com.kuchmynda.mark.safecloud.Common.CommonData;
import cloud.safe.com.kuchmynda.mark.safecloud.Common.SqlData;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Adapters.StructureAdapter;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Database.SqliteManager;
import cloud.safe.com.kuchmynda.mark.safecloud.Infrastructure.Services.DownloadService;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.SafeCloud.FileStructureBase;
import cloud.safe.com.kuchmynda.mark.safecloud.Models.StructureItemModel;
import cloud.safe.com.kuchmynda.mark.safecloud.Presenters.StructurePresenter;
import cloud.safe.com.kuchmynda.mark.safecloud.R;

public class StructureFragment extends Fragment implements FragmentBase {
    public static final int ID = R.layout.fragment_structure;
    private ArrayList<StructureItemModel> structureItemModels;
    private StructureItemModel model;
    private BroadcastReceiver downloadReceiver;
    private StructurePresenter presenter;
    public static final String broadcast_action="UpdateData";

    private String parentId=null;
    final AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            presenter.goTo(position);
        }
    };

    public StructureFragment() {
    }
    public ArrayList<StructureItemModel> getModel() {
        return structureItemModels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = new StructurePresenter();
        }
        presenter.takeView(this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(ID, container, false);
        initView(view);
        return view;
    }

    @Override
    public void initView(View view) {

        final ListView listView = (ListView) view.findViewById(R.id.structureList);

        initArguments();


        listView.setOnItemClickListener(itemClickListener);

        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int state = intent.getIntExtra(CommonData.PARAM_STATE, -1);
                SqliteManager manager = new SqliteManager(getActivity());
                //todo: need fix it and reorganize API to /Structure
                structureItemModels = new ArrayList<>();
                if (state == CommonData.STATUS_ERROR) {
                    presenter.messageHandler("Internal server error.");
                }
                if (state == CommonData.STATUS_FINISH) {
                    String json = intent.getStringExtra(CommonData.FRAGMENT_MODEL_EXTRA);
                    if (json != null) {
                        List<FileStructureBase> structures = (new Gson())
                                .fromJson(json, new TypeToken<ArrayList<FileStructureBase>>() {
                                }.getType());
                        if (structures.size() > 0) {
                            if (getParentId() == null)
                                setParentId(structures.get(0).getParentId());
                            for (FileStructureBase folder : structures) {
                                if (!manager.exists(folder))
                                    manager.insert(folder);
                                else manager.update(folder);
                                /*List<File> files = folder.getFiles();
                                if (files.size() > 0) {
                                    for (File file : files) {
                                        if (!manager.exists(file))
                                            manager.insert(file);
                                        else manager.update(file);
                                    }
                                }*/
                            }
                        }
                    }
                }
                if (getParentId() != null) {
                    String[] tables = new String[]{SqlData.FolderTable, SqlData.FileTable};
                    Cursor cursor;

                    for (String table : tables) {
                        cursor = manager.select(table, SqlData.COLUMN_PARENT_ID + " = ?", getParentId());
                        if (cursor.moveToFirst()) {
                            int idIndex = cursor.getColumnIndex(SqlData.COLUMN_ID);
                            int headerIndex = cursor.getColumnIndex(SqlData.COLUMN_HEADER);
                            int dateIndex = cursor.getColumnIndex(SqlData.COLUMN_CREATED_DATE);
                            int parentIndex = cursor.getColumnIndex(SqlData.COLUMN_PARENT_ID);
                            do {
                                try {
                                    synchronized (structureItemModels){
                                    String parentId = cursor.getString(parentIndex);
                                    structureItemModels.add(new StructureItemModel(cursor.getString(idIndex), parentId, cursor.getString(headerIndex),
                                            (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")).parse(cursor.getString(dateIndex))));}
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            } while (cursor.moveToNext());
                        }
                    }

                    listView.setAdapter(new StructureAdapter(structureItemModels, getActivity()));
                }
            }
        };
        IntentFilter filter = new IntentFilter(broadcast_action);
        getActivity().registerReceiver(downloadReceiver, filter);
    }

    @Override
    public void initArguments() {
        Bundle args=getArguments();
        Activity parent = getActivity();
        TextView view1=(TextView) parent.findViewById(R.id.appbar_header_1);
        TextView view2=(TextView) parent.findViewById(R.id.appbar_header_2);
        if (args!=null && args.containsKey(CommonData.CURRENT_FOLDER)) {
            setParentId(getArguments().getString(CommonData.CURRENT_FOLDER));
            model = (StructureItemModel) getArguments().getSerializable(CommonData.FRAGMENT_MODEL_EXTRA);
            setParentId(model.getId());
            view1.setText(model.getHeader());
            view2.setText("(" + (new SimpleDateFormat("yyyy-MM-dd").format(model.getDate())) + ")");
        }
        else {
            view1.setText("SafeCloud");
            view2.setText("");
        }
        Intent service=new Intent(getActivity(),DownloadService.class);
        service.putExtra(CommonData.CURRENT_FOLDER, getParentId());
        getActivity().startService(service);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(downloadReceiver);
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}