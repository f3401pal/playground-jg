package com.f3401pal.playground.jg.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.f3401pal.playground.jg.R
import com.f3401pal.playground.jg.databinding.FragmentTransactionListBinding
import com.f3401pal.playground.jg.databinding.ItemDaliyTransactionBinding
import com.f3401pal.playground.jg.databinding.ItemTransactionBinding
import com.f3401pal.playground.jg.db.entity.Transaction
import com.f3401pal.playground.jg.db.entity.displayAmount
import com.f3401pal.playground.jg.ext.collectAfterCreated
import com.f3401pal.playground.jg.model.TransactionListViewModel
import com.f3401pal.playground.jg.usecase.DaliyTransactions
import com.google.android.material.divider.MaterialDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionListFragment : Fragment() {

    private val viewModel: TransactionListViewModel by activityViewModels()
    private lateinit var viewBinding: FragmentTransactionListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction_list, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DaliyTransactionListAdapter()
        viewBinding.transactionList.adapter = adapter
        viewModel.daliyTransactions.collectAfterCreated(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}

private class DaliyTransactionListAdapter : ListAdapter<DaliyTransactions, DaliyTransactionViewHolder>(DaliyTransactionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaliyTransactionViewHolder {
        return DaliyTransactionViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_daliy_transaction, parent,false)
        )
    }

    override fun onBindViewHolder(holder: DaliyTransactionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

private class TransactionListAdapter : ListAdapter<Transaction, TransactionViewHolder>(TransactionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_transaction, parent,false)
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}

private class DaliyTransactionViewHolder(
    private val viewBinding: ItemDaliyTransactionBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    private val adapter = TransactionListAdapter()

    init {
        viewBinding.daliyTransactionList.adapter = adapter
        viewBinding.daliyTransactionList.addItemDecoration(MaterialDividerItemDecoration(itemView.context, MaterialDividerItemDecoration.VERTICAL))
    }

    fun bind(daliyTransactions: DaliyTransactions) {
        viewBinding.dateLabel.text = daliyTransactions.localDate.toString()
        adapter.submitList(daliyTransactions.transactions)
    }

}

private class TransactionViewHolder(
    private val viewBinding: ItemTransactionBinding
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(transaction: Transaction) {
        viewBinding.description.text = transaction.description
        viewBinding.amount.text = transaction.displayAmount()
    }

}

private object DaliyTransactionDiffCallback : DiffUtil.ItemCallback<DaliyTransactions>() {

    override fun areItemsTheSame(oldItem: DaliyTransactions, newItem: DaliyTransactions): Boolean {
        return oldItem.localDate == newItem.localDate
    }

    override fun areContentsTheSame(oldItem: DaliyTransactions, newItem: DaliyTransactions): Boolean =
        oldItem.transactions.size == newItem.transactions.size

}

private object TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {

    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean =
        oldItem.description == newItem.description && oldItem.amount == newItem.amount

}