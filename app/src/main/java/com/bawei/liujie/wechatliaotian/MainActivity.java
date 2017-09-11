package com.bawei.liujie.wechatliaotian;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private EditText edText;
    private Button mSend;
    private List<MyData> mData = new ArrayList<>();
    private ListviewAdapter adapter;
    private CheckBox mCheckbox;
    private Button check_button;
    private AudioRecoderUtils mAudioRecoderUtils;
    private MyData mydata;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        listView.setSelection(listView.getCount() - 1);
    }

    private String[] msgArray = new String[]{"你好", "nihao", "在干嘛呢?", "上课啊",
            "嫩那个聊一会吗", "你TM咋不放大呢？留大抢人头啊？CAO！你个菜B", "2B不解释", "尼滚...",
            "我在家，过来吧？", "fresdf", "与热热GRE", "OK,搞起！！"};

    private String[] dataArray = new String[]{"2017-09-09 18:00:02",
            "2017-09-09 18:10:22", "2017-09-09 18:11:24",
            "2017-09-09 18:20:23", "2017-09-09 18:30:31",
            "2017-09-09 18:35:37", "2017-09-09 18:40:13",
            "2017-09-09 18:50:26", "2017-09-09 18:52:57",
            "2017-09-09 18:55:11", "2017-09-09 18:56:45",
            "2017-09-09 18:57:33",};
    private final static int COUNT = 12;// 初始化数组总数

    private void initData() {
        for (int i = 0; i < COUNT; i++) {
            mydata = new MyData();
            mydata.setDate(dataArray[i]);
            if (i % 2 == 0) {
                mydata.setMsgType(true);
            } else {
                mydata.setMsgType(false);
            }
            mydata.setMessage(msgArray[i]);
            mData.add(mydata);
        }
        adapter = new ListviewAdapter(this, mData);
        listView.setAdapter(adapter);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.list_view);
        edText = (EditText) findViewById(R.id.ed_text);
        mSend = (Button) findViewById(R.id.btn);
        check_button = (Button) findViewById(R.id.button);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox);
        mSend.setOnClickListener(this);
        mCheckbox.setOnClickListener(this);

        check_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        check_button.setText("松开保存");
                        mAudioRecoderUtils.startRecord();
                        break;
                    case MotionEvent.ACTION_UP:
                        mAudioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
//                        mAudioRecoderUtils.cancelRecord();    //取消录音（不保存录音文件）
                        check_button.setText("按住说话");
                        break;
                }
                return true;
            }
        });
        mAudioRecoderUtils = new AudioRecoderUtils();

        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {
            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
            }
            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath) {
                Toast.makeText(MainActivity.this, "录音保存在：" + filePath, Toast.LENGTH_SHORT).show();
                mydata.setPath(filePath);
                mData.add(mydata);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void submit() {
        String text = edText.getText().toString().trim();
        if (text.length() > 0) {
            MyData data = new MyData();
            data.setDate(getDate());
            data.setMessage(text);
            data.setMsgType(false);
            mData.add(data);
            adapter.notifyDataSetChanged();
            edText.setText("");
            listView.setSelection(listView.getCount() - 1);
        }
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                InputMethodManager m = (InputMethodManager) edText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                submit();
                break;
            case R.id.checkbox:
                boolean isChecked = mCheckbox.isChecked();
                if (isChecked) {
                    check_button.setVisibility(View.VISIBLE);
                    edText.setVisibility(View.GONE);
                    check_button.setText("按住说话");
                } else {
                    edText.setVisibility(View.VISIBLE);
                    check_button.setVisibility(View.GONE);
                }
                break;
        }

    }


    private String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long l = System.currentTimeMillis();
        return format.format(new Date(l));
    }
}
