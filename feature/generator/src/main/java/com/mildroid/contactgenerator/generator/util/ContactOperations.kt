package com.mildroid.contactgenerator.generator.util

import android.content.ContentProviderOperation
import android.content.Context
import android.provider.ContactsContract
import com.mildroid.contactgenerator.core.log


class ContactOperations(private val context: Context) {

    internal fun prepareContact(displayName: String) {
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
            context
                .contentResolver
                .applyBatch(ContactsContract.AUTHORITY, operations)
        }.recover {
            it.log()
        }
    }
}
