package com.example.groceryapp.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.groceryapp.Data.databasehandler;
import com.example.groceryapp.Model.GroceryM;
import com.example.groceryapp.R;
import java.util.List;
public class recyclerviewadapter extends RecyclerView.Adapter<recyclerviewadapter.viewHolder>
{
    Context context;
    List<GroceryM> list;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    EditText editText1,editText2;
    Button button;
    public recyclerviewadapter() {
    }

    public recyclerviewadapter(Context context, List<GroceryM> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public recyclerviewadapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View v=(View) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listrow,viewGroup, false);
        return new viewHolder(v,context);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewadapter.viewHolder viewHolder, int i)
    {
      GroceryM groceryM=list.get(i);
      viewHolder.textView2.setText(groceryM.getName());
      viewHolder.textView3.setText("Qty-:"+groceryM.getQuantity());
      viewHolder.textView4.setText("DATE-:"+groceryM.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        View itemview;
        TextView textView2,textView3,textView4;
        Button editb,delbutton;
        Context context;
        public viewHolder(@NonNull View itemView,Context context)
        {
            super(itemView);
            this.itemview=itemView;
            this.context=context;
            textView2=(TextView) itemView.findViewById(R.id.textView2);
            textView3=(TextView) itemView.findViewById(R.id.textView3);
            textView4=(TextView) itemView.findViewById(R.id.textView4);
            editb=(Button) itemView.findViewById(R.id.editb);
            delbutton=(Button) itemView.findViewById(R.id.deleteb);
            editb.setOnClickListener(this);
            delbutton.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                }
            });
        }


        @Override
        public void onClick(View v)
        {
         switch (v.getId())
         {
             case R.id.deleteb:
                       delete(getAdapterPosition());
                 break;
             case R.id.editb:
                      edit(getAdapterPosition());
                 break;

         }
        }
    }

    private void edit(final int adapterp)
    {

        builder=new AlertDialog.Builder(context);
        View v=LayoutInflater.from(context).inflate(R.layout.popup,null);
        TextView tv=(TextView) v.findViewById(R.id.textView);
        tv.setText("EDIT ITEM DETAILS");
        editText1=(EditText) v.findViewById(R.id.edittext1);
        editText2=(EditText) v.findViewById(R.id.edittext2);
        databasehandler databasehandler=new databasehandler(context);
        GroceryM groceryM=list.get(adapterp);

        editText1.setText(groceryM.getName());
        editText2.setText(groceryM.getQuantity());
        button=(Button) v.findViewById(R.id.button);
        builder.setView(v);
        alertDialog=builder.create();
        alertDialog.show();
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                if((!editText1.getText().toString().isEmpty())&&(!editText2.getText().toString().isEmpty()))
                {
                    GroceryM groceryM=updatedb(v,editText1.getText()+"",editText2.getText()+"",list.get(adapterp).getId());
                    list.set(adapterp,groceryM);
                    notifyItemChanged(adapterp,groceryM);
                    alertDialog.dismiss();
                }
                else
                {
                    Toast.makeText(context,"ENTER FULL DATA",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private GroceryM updatedb(View v,String name,String qty,String id)
    {
        databasehandler databasehandler=new databasehandler(context);
        GroceryM groceryM=new GroceryM();
        groceryM.setName(name);
        groceryM.setQuantity(qty);
        groceryM.setId(id);
        databasehandler.updateGrocery(groceryM);
        return groceryM;
    }

    private void delete(final int adapterPosition)
    {
       builder=new AlertDialog.Builder(context);
       View v=LayoutInflater.from(context).inflate(R.layout.conformationdialog,null);
       Button buttony=(Button) v.findViewById(R.id.yes);
       Button buttonn=(Button) v.findViewById(R.id.no);

       builder.setView(v);

      alertDialog= builder.create();
              alertDialog.show();

        buttony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                databasehandler db=new databasehandler(context);
                GroceryM groceryM=list.get(adapterPosition);
               // Toast.makeText(context,(groceryM.getId()),Toast.LENGTH_LONG).show();
                int id;
                id=Integer.parseInt(groceryM.getId());
                db.deleteGrocery(id);
                list.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);
                alertDialog.dismiss();

            }
        });
        buttonn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                alertDialog.dismiss();
            }
        });
    }
}
