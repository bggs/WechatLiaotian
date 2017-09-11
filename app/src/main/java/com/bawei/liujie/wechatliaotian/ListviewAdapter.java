package com.bawei.liujie.wechatliaotian;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

/**
 * 类的作用:
 * author: 刘婕
 * date:2017/9/9
 */

public class ListviewAdapter extends BaseAdapter {
    public static interface IMsgViewType {
        int IMVT_COM_MSG = 0;// 收到对方的消息
        int IMVT_TO_MSG = 1;// 自己发送出去的消息
    }

    private Context context;
    private List<MyData> list;
    private static final int ITEMCOUNT = 2;// 消息类型的总数

    public ListviewAdapter(Context context, List<MyData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        MyData myData = list.get(position);
        if (myData.getMsgType()) {//收到的消息
            return IMsgViewType.IMVT_COM_MSG;
        } else {//自己发送的消息
            return IMsgViewType.IMVT_TO_MSG;
        }
    }

    @Override
    public int getViewTypeCount() {
        return ITEMCOUNT;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyData myData = list.get(position);
        boolean isComMsg = myData.getMsgType();
        final ViewHolder holder;
        if (convertView == null) {
            if (isComMsg) {
                convertView = View.inflate(context, R.layout.item_left, null);
            } else {
                convertView = View.inflate(context, R.layout.item_right, null);
            }
            holder = new ViewHolder();
            holder.tvSendTime = (TextView) convertView.findViewById(R.id.tvSendTime);
            holder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            holder.isComMsg = isComMsg;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvSendTime.setText(myData.getDate());
        holder.tvContent.setText(myData.getMessage());
        MyData data = list.get(position);
        MediaPlayer mediaPlayer = new MediaPlayer();
        if (myData.getPath() != null) {
            try {
                mediaPlayer.setDataSource(myData.getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                holder.tvContent.setText(data + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return convertView;
}

static class ViewHolder {
    public TextView tvSendTime;
    public TextView tvContent;
    public boolean isComMsg = true;
}
}
