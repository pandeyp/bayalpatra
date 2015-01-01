/**
 *
 */
package commons

//import hrm.DateSetting
import org.apache.log4j.Logger

import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author Prasanna
 *
 */
class DateUtils {

    //get the logger instance
    private static final Logger LOGGER = Logger.getLogger(DateUtils.class);

    /**
     * @param dateStr
     * @return {@link Date} in 'yyyy-MM-dd' format.
     */
    def static stringToDate(String stringDate) {
        Date date = null;
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(stringDate);
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage());
        }
        return date;
    }

    /**
     * @param dateStr
     * @return {@link Date} in 'yyyy-MM-dd' format.
     */
    def static formatDateToYYYYMMDD(Date date) {
        Date formattedDate
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = stringToDate(formatter.format(date));
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage());
        }
        return formattedDate;
    }


    def static stringToDateTime(String stringDate) {
        Date date = null;
        try{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            date = format.parse(stringDate);
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage());
        }
        return date;
    }

    /**
     * @param date
     * @return <code>Date</code> in 'yyyy-MM-dd' <code>String</code> format.
     */
    def static dateToString(Date date) {
        try{
            SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormatYYYYMMDD.format(date);
        }catch(Exception e){}
    }

    def static dateTimeToString(Date date) {
        try{
            SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            return dateFormatYYYYMMDD.format(date);
        }catch(Exception e){}
    }

    /**
     * @param date
     * @return <code>Date</code> in 'MM-yyyy' from <code>'dd-MM-yyyy'</code> format.
     * applicable for unique salary report generation for the combination of an employee and salary month
     */
    def static toMonthYearFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("MM/yyyy")
        Date newDateFormat = dateFormat.parse(dateFormat.format(date))
        return newDateFormat
    }

    /**
     * Get the current date
     * @return Current date from the database
     */
    def static getCurrentDate(){
        Date curDate
        try{
            curDate = new Date()
            LOGGER.debug("Current Date: " + curDate)
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage());
        }
        return curDate
    }

/*    *//**
     * Responsible to get the corresponding english date settings required for the application
     * @param dateType(like Ghatasthapana, Fiscal Year)
     * @return Corresponding English Date of either Fiscal Year or Date as of Ghatasthapana
     *//*
    def static getSettingDate(String dateType){
        Date settingDate = null
        try{
            def DateSetting dateSetting = DateSetting.findByDateType(dateType)
            settingDate = dateSetting.getDate();
        }catch (Exception e) {
            LOGGER.error("Exception: " + e.getMessage())
        }
        return settingDate
    }*/

    /**
     * @return Total service days (Join date - Currrent date)
     */
    def static getServiceDays(Date joinDate) {
        int numOfDays=0.0;
        def leaveService
        Date currentDate = DateUtils.getCurrentDate();
        numOfDays = (currentDate.getTime() - joinDate.getTime())/(24*60*60*1000);
        return numOfDays;
    }

    /**
     * @return Total days (To date - From date)
     */
    def static getDaysFromTwoDates(Date fromDate, Date toDate) {
        int numOfdays=0;
        numOfdays = ((toDate.getTime() - fromDate.getTime())/(24*60*60*1000))+1
        return numOfdays;
    }

    /**
     * @return Total service days (Date as of Ashad 31 - Join date)
     */
    def static getServiceDaysAsOfAshad31(Date joinDate) {
        int numOfdays= 0;
        Date dateAsOfAshad31 = DateUtils.getSettingDate(BayalpatraConstants.FISCAL_YEAR)
        numOfdays = (dateAsOfAshad31.getTime() - joinDate.getTime())/(24*60*60*1000);
        return numOfdays;
    }

    /**
     * @return Total service days (Date as of Ghatasthapana - Join date)
     */
    def static getServiceDaysAsOfGhatasthapana(Date joinDate) {
        int numOfdays=0;
        Date dateAsOfGhatasthapana = DateUtils.getSettingDate(BayalpatraConstants.GHATASTHAPANA)
        numOfdays = (dateAsOfGhatasthapana.getTime() - joinDate.getTime())/(24*60*60*1000);
        return numOfdays;
    }

    /**
     * Get the Number of days in a month
     * @param date
     * @return Number of days as <code>Integer</code> in a month
     */
    def static getNumberDaysInMonth(Date date){
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        Integer days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        return days
    }

    /**
     * Get days of week
     * @param date
     * @return Days of week as <code>Integer</code>. For eg. Sunday - > 1, Monday - > 2
     */
    def static getDaysOfWeek(Date date){
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        Integer days = calendar.getActualMaximum(Calendar.DAY_OF_WEEK)
        println("Days of week: " + days)
        return days
    }

    def static getSundayOfWeek(){
        def checkDay = formatDateToYYYYMMDD(DateUtils.getCurrentDate())
        def day = getDayFromDate(checkDay)
        while(day>1){
            checkDay=checkDay-1
            day=getDayFromDate(checkDay)
        }
        checkDay= checkDay+1
        return formatDateToYYYYMMDD(checkDay)
    }

    def static checkIfTimeFallsWithinRange(Integer lowerRange, Integer upperRange, Date compareDate){
        def result=false
        def checkHour = getHourFromDate(compareDate)
        if(checkHour<=upperRange && checkHour>=lowerRange) result=true
        return result
    }

    /**
     * Get day from Date
     * @param date
     * @return Day in the Integer format
     */
    def static getDayFromDate(Date date){
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        Integer day = calendar.get(Calendar.DAY_OF_MONTH)
        return day
    }

    /**
     * Get month from Date
     * @param date
     * @return Month in the Integer format
     */
    def static getMonthFromDate(Date date){
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        Integer month = calendar.get(Calendar.MONTH)
        return month
    }

    /**
     * Get Year from Date
     * @param date
     * @return Year in the Integer format
     */
    def static getYearFromDate(Date date){
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        Integer year = calendar.get(Calendar.YEAR)
        return year
    }

    def static formatDateTime(Date date){
        def formatedDate=''
        if(date){
            def chunks = date.toString().split(":")
            formatedDate+=chunks[0]+":"
            formatedDate+=chunks[1]
        }
        return formatedDate
    }

    /**
     * Get Hour from Date
     * @param date
     * @return Hour in the Integer format
     */
    def static getHourFromDate(Date date){
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY)
        return hour
    }

    def static getMinuteFromDate(Date date){
        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        Integer minute = calendar.get(Calendar.MINUTE)
        return minute
    }

    def static getTotalMinuteFromDate(Date date){
        def minutes
        def timeHr = getHourFromDate(date)
        def timeMin = getMinuteFromDate(date)
        minutes = timeHr*60+timeMin
        return minutes
    }

    /**
     * Get hours from date
     * @param date
     * @return Hours in <code>Double</code> format
     */
    def static getTotalHoursFromDate(Date date){
        Double hours = (date.getTime())/(60*60*1000)
        return hours
    }

    def static getDifferenceInHours(Date fromDate, Date toDate){
        def diff = getTotalHoursFromDate(toDate) - getTotalHoursFromDate(fromDate)
        return diff
    }


    /*
    * Generate First Day of Month
    * @param date
    * @return firstDay in {@link Date} format
    **/

    def static getFirstDateOfMonth(){
        Calendar calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        Date monthStartDate=DateUtils.formatDateToYYYYMMDD(calendar.getTime())
        return monthStartDate
    }

    /*
    * Generate Last Date of Month
    * @param date
    * @return lastDay in {@link Date} format
    **/

    def static getLastDateOfMonth(){
        Calendar calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        Date monthEndDate=DateUtils.formatDateToYYYYMMDD(calendar.getTime())
        return monthEndDate
    }

    /**
     * Generate first date from any date
     * @params date
     * @return firstDate of the month of the date received as parameter
     * */


    def static getFirstDateOfAnyMonth(Date date){
        Date monthStartDate
        Calendar calendar= Calendar.getInstance()
        try{
            calendar.setTime(date)
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        }
        catch(Exception e){
            LOGGER.error("Exception :: "+e.getMessage())
        }
        monthStartDate=DateUtils.formatDateToYYYYMMDD(calendar.getTime())
        return monthStartDate
    }

    /**
     * Generate last date from any date
     * @params date
     * @return lastDate of the month of the date received as parameter
     * */

    def static getLastDateOfAnyMonth(Date date){
        Date monthEndDate
        Calendar calendar= Calendar.getInstance()
        try{
            calendar.setTime(date)
            calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        }
        catch(Exception e){
            LOGGER.error("Exception :: "+e.getMessage())
        }
        monthEndDate=DateUtils.formatDateToYYYYMMDD(calendar.getTime())
        return monthEndDate
    }

    /**
     * Generate age from given Date of Birth.
     * @param dob
     * @return Age in years
     */

    def static getAgeFromDOB ( Date dob ) {
        int numOfDays=0.0;
        def leaveService
        Date currentDate = DateUtils.getCurrentDate();
        numOfDays = (currentDate.getTime() - dob.getTime())/(24*60*60*1000);
        return numOfDays/365;
    }

    def static checkIfDateFallsInMonth(Date refDate, Date compareDate){
        def result = false
        if(compareDate){
            int refYear = getYearFromDate(refDate)
            int refMonth = getMonthFromDate(refDate)
            int compYear = getYearFromDate(compareDate)
            int compMonth = getMonthFromDate(compareDate)
            if(refYear==compYear && refMonth==compMonth){
                result= true
            }
        }
        return result
    }

    def static checkIfDateFallsInRange(Date startDate,Date endDate,Date checkDate){
        def result=false
        if(endDate){
            int startYear = DateUtils.getYearFromDate(startDate)
            int startMonth = DateUtils.getMonthFromDate(startDate)
            int endYear = DateUtils.getYearFromDate(endDate)
            int endMonth = DateUtils.getMonthFromDate(endDate)
            int checkYear = DateUtils.getYearFromDate(checkDate)
            int checkMonth = DateUtils.getMonthFromDate(checkDate)
            if(startYear<=checkYear && checkYear>=endYear){
                if(startMonth<=checkMonth && endMonth>=checkMonth){
                    result = true
                }
            }
        }
        return result
    }

}
