package team6.uw.edu.amessage.chat_message_list_recycler_view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team6.uw.edu.amessage.LoginFragment;
import team6.uw.edu.amessage.R;


import java.util.List;

/**
 * This will allow to display all the messages of all the chat messages allowing
 * for them to be put in a recycler view allowing this class to be reused.
 */
public class chatMessageListRecyclerViewAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private List<Messages> mMessageList;

    /**
     * This is the constructor to set up the chat message adapter.
     *
     * @param messageList the incoming list of messages.
     */
    public chatMessageListRecyclerViewAdapter(List<Messages> messageList) {
        mMessageList = messageList;
    }

    /**
     * This will get the list count of the current chat messages.
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    /**
     * Determines the appropriate ViewType according to the sender of the message.
     */
    @Override
    public int getItemViewType(int position) {
        Messages message = (Messages) mMessageList.get(position);

        //Check list index to see if that messages is from the same user logged in to the app.
        if (Integer.parseInt(message.getUserId()) == Integer.parseInt(LoginFragment.mUserId)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    /**
     * Inflates the appropriate layout according to the ViewType.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_box_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_box_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    /**
     * Passes the message object to a chatMessageListRecyclerViewAdapter so that the contents can be bound to UI.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Messages message = (Messages) mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    /**
     * This will be a inner class allowing for messages to be show on the right and left
     * of a chat.
     */
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        /**
         * Constructor to show the messages.
         *
         * @param itemView the view.
         */
        SentMessageHolder(View itemView) {
            super(itemView);
            //This will set the text fields of the sent message
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
        }

        /**
         * This will bind the incoming message to a layout.
         *
         * @param message the incoming message.
         */
        void bind(Messages message) {
            //This will set the text of those text fields.
            messageText.setText(message.getMessage());
            timeText.setText(message.getTimeStamp());
        }
    }

    /**
     * This will be a inner class allowing for messages to be show on the right and left
     * of a chat.
     */
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;

        /**
         * Constructor to show the messages.
         *
         * @param itemView the view.
         */
        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_message_body);
            timeText = (TextView) itemView.findViewById(R.id.text_message_time);
            nameText = (TextView) itemView.findViewById(R.id.text_message_name);
        }

        /**
         * This will bind the incoming message to a layout.
         *
         * @param message the incoming message.
         */
        void bind(Messages message) {
            //This will set the text fields of the received holder.
            messageText.setText(message.getMessage());
            timeText.setText(message.getTimeStamp());
            nameText.setText(message.getUserEmail());

        }
    }

}