package com.bhuvanesh.employeedata.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bhuvanesh.employeedata.database.AppDatabase
import com.bhuvanesh.employeedata.database.EmployeeRecord
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: EmployeeRepository
    // LiveData gives us updated words when they change.

    var insertOperationLiveData: MutableLiveData<Long> = MutableLiveData()
    var updateOperationLiveData: MutableLiveData<Int> = MutableLiveData()
    var deleteOperationLiveData: MutableLiveData<EmployeeRecord> = MutableLiveData()

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }


    init {
//        val employeeDao = EmployeeDatabase.invoke(application).employeeDao()
        // invoke is an operator func, we can call that function without using its name. // Kotlin Operator Overloading.
        val employeeDao = AppDatabase(application).employeeDao()
        repository = EmployeeRepository(employeeDao)
//        employeeListLiveData.value = repository.getAllRecords()
    }

    fun getAllRecords() = repository.getAllRecords()

    fun insert(employee: EmployeeRecord) {
//        insertOperationLiveData = LiveDataReactiveStreams.fromPublisher(repository.insert(employee).toFlowable())

        // just() won't execute the "code" within its parenthesis as just takes a value, not a computation. You need fromCallable:
        /*
        * Kotlin lambdas are fully compatible with Java functional interfaces.
        *
        * You may think that Kotlin just converts the lambda into an anonymous class just like the old times.
        * Well there is a bit of a catch here. When using anonymous classes explicitly, you are always creating a new instance.
        * But in lambda, if it is not capturing, there will exist a single instance of it that is used on every reuse.
        * */
        val disposable = Single.fromCallable { repository.insert(employee) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturnItem(-1)
            .subscribe { id: Long ->
                insertOperationLiveData.value = id
            }
        compositeDisposable.add(disposable)
    }

    fun update(employee: EmployeeRecord) {
        val disposable = Single.fromCallable { repository.update(employee) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturnItem(-1)
            .subscribe { id: Int ->
                updateOperationLiveData.value = id
            }
        compositeDisposable.add(disposable)
    }

    fun delete(employee: EmployeeRecord) {
        val disposable = Single.fromCallable { repository.delete(employee) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                deleteOperationLiveData.value = employee
            }, {})
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}