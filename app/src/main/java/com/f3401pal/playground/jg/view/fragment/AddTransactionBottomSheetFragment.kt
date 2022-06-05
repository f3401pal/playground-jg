package com.f3401pal.playground.jg.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.f3401pal.playground.jg.R
import com.f3401pal.playground.jg.databinding.FragmentAddTransactionBinding
import com.f3401pal.playground.jg.domain.AddTransactionViewModel
import com.f3401pal.playground.jg.domain.model.TransactionInputValidationResult
import com.f3401pal.playground.jg.domain.model.TransactionType
import com.f3401pal.playground.jg.ext.collectAfterCreated
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTransactionBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: AddTransactionViewModel by viewModels()
    private lateinit var viewBinding: FragmentAddTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_transaction, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var type: TransactionType = TransactionType.Income
        viewBinding.typeChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            when(checkedIds.first()) {
                R.id.incomeChip -> type = TransactionType.Income
                R.id.expenseChip -> type = TransactionType.Expense
            }
        }

        // clear errors when value changed
        viewBinding.description.editText?.addTextChangedListener {
            viewBinding.description.error = null
        }
        viewBinding.amount.editText?.addTextChangedListener {
            viewBinding.amount.error = null
        }

        viewBinding.actionSave.setOnClickListener {
            val description = viewBinding.description.editText?.text?.toString()
            val amount = viewBinding.amount.editText?.text?.toString()
            viewModel.saveTransaction(type, description, amount).collectAfterCreated(viewLifecycleOwner) {
                setError(it)
                if(it == TransactionInputValidationResult.Clear) dismiss()
            }
        }
    }

    private fun setError(error: TransactionInputValidationResult) {
        when(error) {
            TransactionInputValidationResult.EmptyDescription ->
                viewBinding.description.error = getString(R.string.error_empty_description)
            TransactionInputValidationResult.EmptyAmount ->
                viewBinding.amount.error = getString(R.string.error_zero_amount)
            TransactionInputValidationResult.InvalidAmount ->
                viewBinding.amount.error = getString(R.string.error_not_digit)
            TransactionInputValidationResult.Clear -> {
                viewBinding.description.error = null
                viewBinding.amount.error = null
            }
        }
    }

    companion object {
        const val TAG = "AddTransactionBottomSheetFragment"
    }
}