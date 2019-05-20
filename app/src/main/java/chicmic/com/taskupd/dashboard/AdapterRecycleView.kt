package chicmic.com.taskupd.dashboard

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import chicmic.com.taskupd.R
import chicmic.com.taskupd.responsemodel.CustomerData


class AdapterRecycleView (private val myDataset: MutableList<CustomerData>) : RecyclerView.Adapter<AdapterRecycleView.ViewHolder>() {




    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val name=itemView!!.findViewById<TextView>(R.id.name)
        val fName=itemView!!.findViewById<TextView>(R.id.fname)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val customerData:CustomerData=myDataset[position]
        holder.name.text=customerData.name
        holder.fName.text=customerData.id
        Log.d("bind",customerData.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.item_row,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return myDataset.size
        Log.d("bind","size")
    }
    fun addUser(customerData: CustomerData) {
        myDataset.add(customerData)
        notifyDataSetChanged()
    }









}