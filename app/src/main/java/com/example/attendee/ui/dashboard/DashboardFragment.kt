package com.example.attendee.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendee.AttendeeApplication
import com.example.attendee.AttendeeListAdapter
import com.example.attendee.CreateNewAttendeeDialogFragment
import com.example.attendee.R
import com.example.attendee.database.AttendeeEntity
import com.example.attendee.database.ProfileEntity
import com.example.attendee.databinding.FragmentDashboardBinding
import com.example.attendee.viewmodel.AttendeeViewFactory
import com.example.attendee.viewmodel.AttendeeViewModel
import com.example.attendee.viewmodel.ProfileViewModel
import com.example.attendee.viewmodel.ProfileViewModelFactory

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val attendeeViewModel: AttendeeViewModel by viewModels {
        AttendeeViewFactory((requireActivity().applicationContext as AttendeeApplication).attendeeRepository)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = AttendeeListAdapter()
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.createNewAttendeeSheet.setOnClickListener{
            val createattendeedialog = CreateNewAttendeeDialogFragment()
            createattendeedialog.show((requireActivity()).supportFragmentManager,"")
            val profileEntity =
                AttendeeEntity(0,"ahokusa","fahokusa","ahokussa","private","public")
            attendeeViewModel.insert(profileEntity)
        }


        binding.recylcerView.adapter = adapter
        binding.recylcerView.layoutManager = LinearLayoutManager(context)
        attendeeViewModel.allAttendee.observe(viewLifecycleOwner,Observer { attendee ->
            attendee.let { adapter.submitList(it) }
        })

        val helper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // どのような動きを許可するか
            // ViewHolder ごとに分ける等の場合はここで制御す
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            // スワイプされた場合
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // 項目を消去
                viewHolder.itemId
                adapter.notifyDataSetChanged()
            }

            // 選択状態が変化した時に呼ばれる
            // 選択が解除された場合 viewHolder は null になるので #clearView で操作する
            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                // e.g. 半透明にする
                when (actionState) {
                    ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.ACTION_STATE_SWIPE -> {
                        (viewHolder as? AttendeeListAdapter.WordViewHolder)?.let {
                            it.itemView.alpha = 0.5f
                        }
                    }
                }
            }

            // アニメーションが終了する時に呼ばれる
            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                // e.g. 反透明にしていたのを元に戻す
                (viewHolder as AttendeeListAdapter.WordViewHolder).itemView.alpha = 1.0f
            }
        })
        helper.attachToRecyclerView(binding.recylcerView)



        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}