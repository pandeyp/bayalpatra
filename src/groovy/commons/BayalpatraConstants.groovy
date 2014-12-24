package commons

/**
 * Created by Prasanna on 1/24/2014.
 */
class BayalpatraConstants {

    //Build war for the client below
    static final String CLIENT_NAME = "BP"
    static final String CLIENT_DEERWALK = "DW"
    static final String CLIENT_BAYALPATRA = "BP"

    static final String ADMIN_CONTACT_PERSON = "abc@deerwalk.com"

    //employee status
    static final String PERMANENT = "Permanent"
    static final String VOLUNTEER = "Volunteer"
    static final String TERMINATED = "Terminated"
    static final String CLEARED = "Cleared"
    static final String SUSPENDED = "Suspended"
    static final String DAILY_WAGES = "Trainee"
    static final String TEMPORARY = "Temporary"
    static final String CONTRACT = "Contract"
    static final String CONSULTANT = "Consultant"

    //FOR Deerwalk Services

    static final String PART_TIME = "Part time"
    static final String INTERN = "Intern"
    static final String PROBATION = "Probation"
    static final String TRAINEE = "Trainee"
    //Year constants
    static final int ONEMONTH = 30
    static final int SIXMONTH = 182
    static final int ONEYEAR = 365
    static final int TWOYEAR = 730
    static final int THREEYEAR = 1095
    static final int FIVEYEAR = 1825
    static final int SEVENYEAR = 2555
    static final int TENYEAR = 3650
    static final int FIFTEENYEAR = 5475

    //Date Type Constants
    static final String FISCAL_YEAR = "Fiscal Year"
    static final String GHATASTHAPANA = "Ghatasthapana"
    static final String PUBLIC_HOLIDAY = "Public Holiday"

    //Gender Constants
    static final String FEMALE = "Female"
    static final String MALE = "Male"

    //Marital Status
    static final String MARRIED = "Married"
    static final String SINGLE = "Single"
    static final String DIVORCED = "Divorced"
    static final String WIDOWED = "Widowed"

    //Shift Constants
    static final String MORNING_SHIFT = "Morning"
    static final String DAY_SHIFT = "Day"
    static final String EVENING_SHIFT = "Evening"
    static final String NIGHT_SHIFT = "Night"

    static final String MORNING_EVENING_NIGHT_SHIFT = "Morning, Evening, Night"
    static final String MORNING_EVENING_SHIFT = "Morning, Evening"
    static final String MORNING_NIGHT_SHIFT = "Morning, Night"
    static final String DAY_NIGHT_SHIFT = "Day, Night"
    static final String EVENING_NIGHT_SHIFT = "Evening, Night"

    //Leave Constants
    static final String PAID_LEAVE = "Paid"
    static final String UNPAID_LEAVE = "Unpaid"
    static final String LEAVE_APPROVED = "Approved"
    static final String LEAVE_UNAPPROVED = "Unapproved"
    static final String LEAVE_DENIED = "Denied"
    static final String LEAVE_CANCELLED = "Cancelled"
    static final String PERSONAL_LEAVE = "Personal"
    static final String SICK_LEAVE = "Sick"
    static final String WEEK_OFF_LEAVE = "Weekoff"
    static final String DAY_OFF_LEAVE = "Day Off"
    static final String NIGHT_OFF_LEAVE = "Night Off"
    static final String ANNUAL_LEAVE = "Annual Leave"
    static final String SUBSTITUTE_LEAVE = "Substitute Leave"
    static final String FESTIVAL_OFF_LEAVE = "Festival Off"
    static final String LEAVE_TYPE_ALL = "%"

    //FieldType Constants
    static final String FIELD_DESIGNATION = "Designation"
    static final String FIELD_UNIT = "Unit"
    static final String FIELD_SERVICE_TYPE = "Service Type"
    static final String FIELD_DEPARTMENT = "Department"

    //BlockedAllowances Constants
    static final String BLOCKED_ALLOWANCE = "Blocked"
    static final String UNBLOCKED_ALLOWANCE = "Unblocked"
    static final String ADDITIONAL_SALARY = "Additional Salary"
    static final String DASHAIN_EXGRATIA = "Dashain Exgratia"
    static final String DEARNESS_ALLOWANCE = "Dearness Allowance"
    static final String SENIORITY_ALLOWANCE = "Seniority Allowance"
    static final String GRADE = "Grade"
    static final String EXTRA_ALLOWANCE = "Extra Allowance"
    static final String PF = "Provident Fund"
    static final String GRATUITY = "Gratuity"
    static final String OTHER_ALLOWANCE = "Other Allowance"
    static final String BASIC_SALARY = "Basic Salary"
    static final String NIGHT_ALLOWANCE = "Night Allowance"

    //Timecard Constants
    static final String NO_PUNCH_OUT = "No Punch Out"
    static final String NO_PUNCH_IN = "No Punch In"
    static final String NO_PUNCH_IN_AND_PUNCH_OUT = "No Punch In And Punch Out"
    static final String INVALID = "Invalid"
    static final String PASS = "Pass"
    static final String PUNCHIN = "Punch In"
    static final String PUNCHOUT = "Punch Out"

    //Status Constants
    static final String STATUS_ACTIVE = "Active"
    static final String STATUS_DISABLED = "Disabled"

    static final Double FULL_MONTH = 0.0;

    //Attendance Constants
    static final String REGULAR_SHIFT = "Regular"
    static final String IRREGULAR_SHIFT = "Irregular"
    static final Integer PUNCH_IN_LOWER_LIMIT = 60
    static final Integer PUNCH_IN_UPPER_LIMIT = 15
    //It is inclusive of the next hour- if the desirable punch in time is 17:00(5 pm) then it accepts values from 16:00:00 to 18:59:59 (1 sec less than 2 hours)

    static final Integer PUNCH_OUT_LOWER_LIMIT = 15
    static final Integer PUNCH_OUT_UPPER_LIMIT = 15
    // It is inclusive of the next hour if the desirable punch out time is 1700hrs then it accepts values from 1500hrs to 18:59:59hrs.

    //Role Constants
    static final String ROLE_ADMIN = "ROLE_ADMIN"
    static final String ROLE_DEPARTMENT_HEAD = "ROLE_DEPARTMENTHEAD"
    static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE"
    static final String ROLE_SUPERVISOR = "ROLE_SUPERVISOR"
    static final String ROLE_NONE = "None"

    //Designation Constants

    static final String DESIGNATION_EXECUTIVE_DIRECTOR = "Executive Director"

    //Department Constants

    static final String DEPARTMENT_ADMIN = "Administration"

}