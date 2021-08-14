package com.mildroid.contactgenerator.core

import android.content.ContentProviderOperation
import android.content.Context
import android.provider.ContactsContract


class ContactOperations(private val context: Context) {

    private val contentResolver by lazy {
        context.contentResolver
    }

    fun prepareContact(displayName: String) {
//        display names and the numbers are equals.

        val operations = ArrayList<ContentProviderOperation>()

        //insert raw contact using RawContacts.CONTENT_URI
        operations.add(
            ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        )

        //insert contact display name using Data.CONTENT_URI
        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                    ContactsContract.Data.RAW_CONTACT_ID,
                    0
                ).withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                ).withValue(
                    ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                    displayName
                ).build()
        )

        //insert mobile number using Data.CONTENT_URI
        operations.add(
            ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(
                    ContactsContract.Data.RAW_CONTACT_ID,
                    0
                ).withValue(
                    ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                ).withValue(
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    displayName
                ).withValue(
                    ContactsContract.CommonDataKinds.Phone.TYPE,
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                ).build()
        )

        insertContact(operations)
    }

    private fun insertContact(operations: ArrayList<ContentProviderOperation>) {
        runCatching {
            contentResolver
                .applyBatch(ContactsContract.AUTHORITY, operations)
        }.recover {
            it.log("adder")
        }
    }

    fun prepareRemover() {

        val rawUri = ContactsContract.RawContacts.CONTENT_URI.buildUpon()
            .appendQueryParameter(ContactsContract.CALLER_IS_SYNCADAPTER, "true").build()

        val operations: ArrayList<ContentProviderOperation> = ArrayList()

        operations.add(
            ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI).withSelection(
                ContactsContract.RawContacts._ID + ">? "
                , arrayOf("-1")
            ).build()
        ) //sets deleted flag to 1

        operations.add(
            ContentProviderOperation.newDelete(rawUri).withSelection(
                ContactsContract.RawContacts._ID + ">? "
                , arrayOf("-1")
            ).build()
        ) //erases

        removeContacts(operations)
    }

    private fun removeContacts(operations: ArrayList<ContentProviderOperation>) {
        runCatching {
            contentResolver
                .applyBatch(ContactsContract.AUTHORITY, operations)
        }.recover {
            it.log("remover")
        }
    }
}
