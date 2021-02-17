package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eloquent.R;

import java.util.List;

import Model.Listitem;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private Context context;
    private List<Listitem> lisitems;
    public MyAdapter(Context context, List listitem) {
        this.context = context;
        this.lisitems = listitem;

    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        Listitem item = lisitems.get(position);
        holder.Question.setText(item.getQuestion());
             holder.Answer.setText(item.getAnswer());
    }

    @Override
    public int getItemCount() {
        return lisitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Question;
        private  TextView Answer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Question = itemView.findViewById(R.id.textQuestion);
            Answer = itemView.findViewById(R.id.textViewAnswer);
        }


    }
}