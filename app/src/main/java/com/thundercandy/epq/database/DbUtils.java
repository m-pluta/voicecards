package com.thundercandy.epq.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.thundercandy.epq.data.Card;
import com.thundercandy.epq.data.Category;
import com.thundercandy.epq.data.SelectableCategory;
import com.thundercandy.epq.database.CategoryContract.CardEntry;
import com.thundercandy.epq.database.CategoryContract.CategoryEntry;

import java.util.ArrayList;
import java.util.Arrays;

public class DbUtils {

    public static void addDebugData(Context context) {
        ArrayList<Category> categories = new ArrayList<>();

        Card a = new Card(1, "Work done", "The amount of force needed to move an object a certain distance.");
        Card b = new Card(2, "Momentum", "The product of the mass and velocity of an object.");
        Card c = new Card(3, "Centripetal Force", "A force, orthogonal to the direction of motion of an object which causes the object to move in a circular path");
        Card d = new Card(4, "FDE Cycle", "The FDE cycle is followed by a processor to process an instruction.");
        Card e = new Card(5, "Hardware", "The physical components of a computer system");
        Card f = new Card(6, "Software", "Instructions that tell a computer what to do");
        Card g = new Card(7, "Vector", "A quantity with both a magnitude and direction");

        categories.add(new Category(1, "Physics", new ArrayList<>(Arrays.asList(a, b, c))));
        categories.add(new Category(2, "Computer Science", new ArrayList<>(Arrays.asList(d, e, f))));
        categories.add(new Category(2, "Maths", new ArrayList<>(Arrays.asList(g))));

        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        for (Category cat : categories) {
            ContentValues values = new ContentValues();
            values.put(CategoryEntry.COLUMN_NAME, cat.getName());
            values.put(CategoryEntry.COLUMN_DATE_CREATED, 0);                       //TODO: change this later on

            long categoryID = db.insert(CategoryEntry.TABLE_NAME, null, values);

            for (Card card : cat.getCards()) {
                values = new ContentValues();
                values.put(CardEntry.CATEGORY_ID, categoryID);
                values.put(CardEntry.COLUMN_TERM, card.getTerm());
                values.put(CardEntry.COLUMN_DEFINITION, card.getDefinition());
                values.put(CardEntry.COLUMN_DATE_CREATED, 0);                       //TODO: change this later on

                db.insert(CardEntry.TABLE_NAME, null, values);
            }
        }
    }

    public static ArrayList<Category> getCategories(Context context) {
        ArrayList<Category> categories = new ArrayList<>();

        Database helper = new Database(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                CategoryEntry._ID,
                CategoryEntry.COLUMN_NAME
        };

        String orderBy = CategoryEntry._ID + " DESC";   //Change this to CategoryEntry.COLUMN_DATE_CREATED after dates are fixed

        Cursor c = db.query(CategoryEntry.TABLE_NAME,
                projection, null, null, null, null, orderBy, null);

        try {
            while (c.moveToNext()) {
                int cat_id = c.getInt(c.getColumnIndexOrThrow(CategoryEntry._ID));
                String category_name = String.valueOf(c.getString(c.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME)));

                categories.add(new Category(cat_id, category_name, fetchCategoryCards(context, cat_id)));
            }
        } finally {
            c.close();
        }

        return categories;
    }

    public static ArrayList<Card> fetchCategoryCards(Context context, int id) {
        ArrayList<Card> cards = new ArrayList<>();

        Database helper = new Database(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = new String[]{
                CardEntry._ID,
                CardEntry.COLUMN_TERM,
                CardEntry.COLUMN_DEFINITION,
                CardEntry.COLUMN_DATE_CREATED
        };
        String selection = CardEntry.CATEGORY_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        String orderBy = CardEntry._ID + " DESC";   //Change this to CardEntry.COLUMN_DATE_CREATED after dates are fixed

        Cursor c = db.query(CardEntry.TABLE_NAME,
                projection, selection, selectionArgs, null, null, orderBy, null);

        try {
            while (c.moveToNext()) {
                int card_id = c.getInt(c.getColumnIndexOrThrow(CardEntry._ID));
                String term = String.valueOf(c.getString(c.getColumnIndexOrThrow(CardEntry.COLUMN_TERM)));
                String definition = String.valueOf(c.getString(c.getColumnIndexOrThrow(CardEntry.COLUMN_DEFINITION)));

                cards.add(new Card(card_id, term, definition));
            }
        } finally {
            c.close();
        }

        return cards;
    }

    public static int addCategory(Context context, String name, int date) {
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryEntry.COLUMN_NAME, name);
        values.put(CategoryEntry.COLUMN_DATE_CREATED, date);

        return (int) db.insert(CategoryEntry.TABLE_NAME, null, values);
    }

    public static void removeCategory(Context context, int id, boolean removeChildren) {

        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = CategoryEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        int deletedRows = db.delete(CategoryEntry.TABLE_NAME, selection, selectionArgs);
        Toast.makeText(context, deletedRows + " categories deleted", Toast.LENGTH_SHORT).show();

        if (removeChildren) {
            removeCardsWithCatId(context, id);
        }
    }

    private static void removeCardsWithCatId(Context context, int id) {
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = CardEntry.CATEGORY_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        int deletedRows = db.delete(CardEntry.TABLE_NAME, selection, selectionArgs);
        Toast.makeText(context, deletedRows + " cards deleted", Toast.LENGTH_SHORT).show();
    }

    public static int countCategories(Context context) {
        return (int) countEntries(context, CategoryEntry.TABLE_NAME);

    }

    public static int countCards(Context context) {
        return (int) countEntries(context, CardEntry.TABLE_NAME);

    }

    private static long countEntries(Context context, String tableName) {
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(db, tableName);
        return count;
    }

    public static void removeCard(Context context, int id) {
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = CardEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};
        int deletedRows = db.delete(CardEntry.TABLE_NAME, selection, selectionArgs);
        Toast.makeText(context, deletedRows + " cards deleted", Toast.LENGTH_SHORT).show();
    }

    public static int addCard(Context context, int categoryID, String term, String definiton, int date) {
        Database helper = new Database(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CardEntry.CATEGORY_ID, categoryID);
        values.put(CardEntry.COLUMN_TERM, term);
        values.put(CardEntry.COLUMN_DEFINITION, definiton);
        values.put(CardEntry.COLUMN_DATE_CREATED, date);

        return (int) db.insert(CardEntry.TABLE_NAME, null, values);
    }

    public static ArrayList<SelectableCategory> getSelectableCategories(Context context) {
        ArrayList<SelectableCategory> output = new ArrayList<>();

        String sql = "SELECT cats." + CategoryEntry._ID + ", " + CategoryEntry.COLUMN_NAME + ", cat.categoryCount AS CategoryCount" +
                " FROM " + CategoryEntry.TABLE_NAME + " AS cats" +
                " LEFT JOIN" +
                " (SELECT " + CardEntry.CATEGORY_ID + ", COUNT(" + CardEntry.CATEGORY_ID + ") AS categoryCount" +
                " FROM " + CardEntry.TABLE_NAME + " AS cards" +
                " GROUP BY " + CardEntry.CATEGORY_ID + ")" +
                " AS cat" +
                " ON cats." + CategoryEntry._ID + " = cat." + CardEntry.CATEGORY_ID +
                " ORDER BY cat.categoryCount DESC";

        Database helper = new Database(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        try {
            while (c.moveToNext()) {
                int cat_id = c.getInt(c.getColumnIndexOrThrow(CategoryEntry._ID));
                String cat_name = c.getString(c.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME));
                int cat_count = c.getInt(c.getColumnIndexOrThrow("CategoryCount"));
                if (cat_count != 0) {
                    SelectableCategory sc = new SelectableCategory(cat_id, cat_name, cat_count, false);
                    output.add(sc);
                }
            }
        } finally {
            c.close();
        }
        return output;
    }
}
