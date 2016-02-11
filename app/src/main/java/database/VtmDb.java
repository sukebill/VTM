package database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import data.Ability;
import data.Clan;
import data.Discipline;
import data.Path;
import data.Ritual;

public class VtmDb {

    private static final String TABLE_CLANS = "clans";
    private static final String TABLE_DISCIPLINES = "disciplines";
    private static final String TABLE_CLAN_DISCIPLINES = "clan_disciplines";
    private static final String TABLE_RELATION_A_D_P = "relation_a_d_p";
    private static final String TABLE_ABILITIES = "abilities";
    private static final String TABLE_PATHS = "paths";
    private static final String TABLE_RELATION_D_R = "relation_d_r";
    private static final String TABLE_RITUALS = "rituals";

    public List<Discipline> getDisciplines(SQLiteDatabase db){
        List<Discipline> disciplines = new ArrayList<>();
        String query = "SELECT  title,subtitle FROM " + TABLE_DISCIPLINES;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Discipline discipline = new Discipline();
                discipline.setTitle(cursor.getString(0));
                discipline.setSubtitle(cursor.getString(1));
                disciplines.add(discipline);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return disciplines;
    }

    public List<Discipline> getDisciplinesById(SQLiteDatabase db, List<Integer> ids){
        List<Discipline> disciplines = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++){
            String query = "SELECT  title FROM " + TABLE_DISCIPLINES + " WHERE id=" + ids.get(i);
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                Discipline discipline = new Discipline();
                discipline.setId(ids.get(i));
                discipline.setTitle(cursor.getString(0));
                disciplines.add(discipline);
            }
            cursor.close();
        }
        return disciplines;
    }

    public List<Discipline> getDisciplinesInfo(SQLiteDatabase db) {
        List<Discipline> disciplines = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_DISCIPLINES;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Discipline discipline = new Discipline();
                discipline.setId(cursor.getInt(0));
                discipline.setTitle(cursor.getString(1));
                discipline.setDescription(cursor.getString(2));
                discipline.setBonusDescription(cursor.getString(5));
                discipline.setOfficialAbilities(cursor.getString(6));
                discipline.setRituals(cursor.getString(7));
                discipline.setRitualsSystem(cursor.getString(8));
                discipline.setSubtitle(cursor.getString(9));
                disciplines.add(discipline);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return disciplines;
    }

    public List<Clan> getClans(SQLiteDatabase db){
        List<Clan> clans = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_CLANS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Clan clan = new Clan();
                clan.setId(cursor.getInt(0));
                clan.setClan(cursor.getString(1));
                clan.setCaste(cursor.getString(2));
                clan.setSubtitle(cursor.getString(14));
                clans.add(clan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return clans;
    }

    public Clan getClanById(SQLiteDatabase db, int id){
        Clan clan = new Clan();
        String query = "SELECT  * FROM " + TABLE_CLANS + " WHERE id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            clan.setId(id);
            clan.setClan(cursor.getString(1));
            clan.setCaste(cursor.getString(2));
        }
        cursor.close();
        return clan;
    }

    public List<Clan> getClansInfo(SQLiteDatabase db){
        List<Clan> clans = new ArrayList<>();
        String query = "SELECT  * FROM " + TABLE_CLANS;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Clan clan = new Clan();
                clan.setId(cursor.getInt(0));
                clan.setClan(cursor.getString(1));
                clan.setCaste(cursor.getString(2));
                clan.setLittleDesc(cursor.getString(3));
                clan.setNickname(cursor.getString(4));
                clan.setDesc(cursor.getString(5));
                clan.setSect(cursor.getString(6));
                clan.setAppearance(cursor.getString(7));
                clan.setHaven(cursor.getString(8));
                clan.setBackground(cursor.getString(9));
                clan.setCharacterCreation(cursor.getString(10));
                clan.setWeakness(cursor.getString(11));
                clan.setOrganization(cursor.getString(12));
                clan.setBloodlines(cursor.getString(13));
                clan.setSubtitle(cursor.getString(14));
                clans.add(clan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return clans;
    }

    public List<Discipline> getClanDisciplines(SQLiteDatabase db, int clanId){
        List<Integer> disciplineIds= new ArrayList<>();
        String query = "SELECT  id_discipline FROM " + TABLE_CLAN_DISCIPLINES + " WHERE id_clan=" + clanId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                disciplineIds.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return getDisciplinesById(db, disciplineIds);
    }

    public List<Ability> getAbilitiesOfDiscipline(SQLiteDatabase db, int id){
        List<Ability> abilities = new ArrayList<>();
        String query = "SELECT  ability_id FROM " + TABLE_RELATION_A_D_P + " WHERE discipline_id="
                + id + " AND path_id=" + 0;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                abilities.add(getAbilityById(db, cursor.getInt(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return abilities;
    }

    public Ability getAbilityById(SQLiteDatabase db, int id){
        String query = "SELECT  * FROM " + TABLE_ABILITIES + " WHERE id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        Ability ability = new Ability();
        if (cursor.moveToFirst()) {
            ability.setId(id);
            ability.setTitle(cursor.getString(1));
            ability.setDescription(cursor.getString(2));
            ability.setSystem(cursor.getString(3));
            ability.setLevel(cursor.getInt(4));
        }
        cursor.close();
        return ability;
    }

    public List<Clan> getInclan(SQLiteDatabase db, int id){
        List<Clan> clans = new ArrayList<>();
        String query = "SELECT  id_clan FROM " + TABLE_CLAN_DISCIPLINES + " WHERE id_discipline=" + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                clans.add(getClanById(db, cursor.getInt(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return clans;
    }

    public List<Path> getPathsOfDiscipline(SQLiteDatabase db, int id){
        List<Path> paths = new ArrayList<>();
        String query = "SELECT  distinct path_id FROM " + TABLE_RELATION_A_D_P + " WHERE discipline_id="
                + id + " AND path_id!=" + 0;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                paths.add(getPathById(db, cursor.getInt(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return paths;
    }

    public Path getPathById(SQLiteDatabase db, int id){
        Path path = new Path();
        String query = "SELECT  * FROM " + TABLE_PATHS + " WHERE id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            path.setId(cursor.getInt(0));
            path.setName(cursor.getString(1));
            path.setAttribute(cursor.getString(2));
            path.setDescription(cursor.getString(3));
            path.setSystem(cursor.getString(4));
            path.setOfficial(cursor.getString(5));
            path.setPrice(cursor.getString(6));
        }
        return path;
    }

    public List<Ritual> getRitualsOfDiscipline(SQLiteDatabase db, int id){
        List<Ritual> rituals = new ArrayList<>();
        String query = "SELECT  ritual_id FROM " + TABLE_RELATION_D_R + " WHERE discipline_id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                rituals.add(getRitualById(db, cursor.getInt(0)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return rituals;
    }

    public Ritual getRitualById(SQLiteDatabase db, int id){
        Ritual ritual = new Ritual();
        String query = "SELECT  * FROM " + TABLE_RITUALS + " WHERE id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            ritual.setId(cursor.getInt(0));
            ritual.setName(cursor.getString(1));
            ritual.setDescription(cursor.getString(2));
            ritual.setSystem(cursor.getString(3));
            ritual.setLevel(cursor.getInt(4));
            ritual.setSide(cursor.getString(5));
        }
        cursor.close();
        return ritual;
    }

    public int getMaxLevelOfAbilitiesForDiscipline(SQLiteDatabase db, int id){
        int maxLevel = 0;
        String query = "select max(level) from " + TABLE_ABILITIES + " where id in " +
                "( select ability_id from " + TABLE_RELATION_A_D_P + " where discipline_id=" + id + ")";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            maxLevel = cursor.getInt(0);
        }
        cursor.close();
        return maxLevel;
    }

    public int getMinLevelOfAbilitiesForDiscipline(SQLiteDatabase db, int id){
        int minLevel = 0;
        String query = "select min(level) from " + TABLE_ABILITIES + " where id in " +
                "( select ability_id from " + TABLE_RELATION_A_D_P + " where discipline_id=" + id + ") " +
                "and level!=0";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            minLevel = cursor.getInt(0);
        }
        cursor.close();
        return minLevel;
    }

    public List<Ability> getAAbilitiesOfDisciplineByLevel(SQLiteDatabase db, int id, int level){
        List<Ability> abilities = new ArrayList<>();
        String query = "select * from " + TABLE_ABILITIES + " where id in ( select ability_id from "
                + TABLE_RELATION_A_D_P + " where discipline_id=" + id + ") and level=" + level;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Ability ability = new Ability();
                ability.setId(cursor.getInt(0));
                ability.setTitle(cursor.getString(1));
                ability.setDescription(cursor.getString(2));
                ability.setSystem(cursor.getString(3));
                ability.setLevel(level);
                abilities.add(ability);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return abilities;
    }

}