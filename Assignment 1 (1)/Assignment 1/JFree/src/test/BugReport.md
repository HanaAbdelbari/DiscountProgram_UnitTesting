# 🐞 Bug Report – DiscountManagerTest

**Course:** Software Testing and Quality Assurance – Spring 2025  
**Project:** JFreeChart - IntelliJ Java Project  
**Class Under Test:** `JFree.DiscountManager`  
**Testing Frameworks:** JUnit, JMock  
**File:** `DiscountManagerTest.java`

---

## 🔬 Test Case Summary
### DiscountCalculator Tests

| Test Method | Scenario | Expected | Actual | Status | Notes |
|-------------|----------|----------|--------|--------|-------|
| `testIsTheSpecialWeek_Week26` | Week 26 (Special) | `true` | `true` | ✅ Passed | |
| `testIsTheSpecialWeek_Week25` | Week 25 (Non-Special) | `false` | `false` | ✅ Passed | |
| `testGetDiscountPercentage_EvenWeek` | Week 12 (Even) | `7` | `7` | ✅ Passed | |
| `testGetDiscountPercentage_OddWeek` | Week 11 (Odd) | `5` | `5` | ✅ Passed | |
| `testGetDiscountPercentage_Week1` | First week of year | `5` | `5` | ✅ Passed | |
| `testGetDiscountPercentage_Week52` | Last week of year | `7` | `7` | ✅ Passed | |
| `testIsTheSpecialWeek_Week53` | Week 53 (Invalid) | `false` | ❌ Exception | ❌ Failed | Unhandled edge case |
| `testConstructor_NullWeek` | Null input | `Exception` | ❌ NPE | ❌ Failed | No null check |


### DiscountManager Tests

| Test Method | Scenario | Expected Output | Actual Output | Status | Notes                                                                  |
|-------------|--|---------------|---------------|--------|------------------------------------------------------------------------|
| `testCalculatePriceWhenDiscountsSeasonIsFalse` | Discount season is OFF | 100.0 | **100.0**     | ✅ Passed | Skips discount calculation.                                            |
| `testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsTrue` | Discount season ON, special week | 160.0 | **160.0**     | ✅ Passed | Applies fixed 20% discount.                                            |
| `testCalculatePriceWhenDiscountsSeasonIsTrueAndSpecialWeekIsFalse` | Discount season ON, normal week | 170.0 (15% off 200) | **17000.0**   | ❌ Failed | ⚠️ Critical Bug: Percentage multiplier misapplied (85 → 8500%).        |
| `testPercentageConversion` | 50% discount input | 50.0 | **5000.0**    | ❌ Failed | ⚠️ Bug: Percentage used as integer (50 → 50.0), should divide by 100.  |
| `testCalculatePriceWithNegativePrice` | Negative price input | -80.0 | **-80.**      | ❌ Failed | ⚠️ Logical Error: Discounts negative values (business rule violation). |
| `testZeroPrice` | Zero input | 0.0 | **0.0**       | ✅ Passed | Edge case works as expected.                                           |
| `test100PercentDiscount` | 100% discount input | 0.0 | **20000.0**   | ❌ Failed | ⚠️ Critical Bug: 100% → 100x multiplier.                               |
| `testWeek25NotSpecialWeek` | Non-special week | 90.0 | **9000.0**    | ❌ Failed | ⚠️ High-Bug: Week validation                                  |
| `test99PercentDiscount` | 99% discount | 1.0 | **100.0**     | ❌ Failed | ⚠️ High-Bug:Boundary condition                                 |

### Year Tests
# Year Class Test Report


| Test Method | Scenario | Expected | Actual | Status | Notes |
|-------------|----------|----------|--------|--------|-------|
| `testYearDefaultCtor` | Default constructor | Current year (2025) | 2025 | ✅ Passed | |
| `testYearIntCtor` | Constructor with year 2000 | 2000 | 2000 | ✅ Passed | |
| `testConstructorWithDate` | Create from Date object | 2008 | 2008 | ✅ Passed | |
| `testConstructorWithDateTimeZoneLocale` | Create with UTC locale | 2015 | 2015 | ✅ Passed | |

### ⚠️ Boundary Tests
| Test Method | Scenario | Expected | Actual | Status | Notes |
|-------------|----------|----------|--------|--------|-------|
| `testMinimumYearBoundary` | MINIMUM_YEAR (-9999) | -9999 | -9999 | ✅ Passed | |
| `testMaximumYearBoundary` | MAXIMUM_YEAR (9999) | 9999 | 9999 | ✅ Passed | |

### ⏱️ Time Calculation Tests
| Test Method | Scenario | Expected | Actual | Status | Notes |
|-------------|----------|----------|--------|--------|-------|
| `testGetFirstMillisecond` | First ms of 2023 | Correct timestamp | Correct timestamp | ✅ Passed | |
| `testGetLastMillisecond` | Last ms of 2023 | Correct timestamp | Correct timestamp | ✅ Passed | |

### 🔄 Comparison Tests
| Test Method | Scenario | Expected | Actual | Status | Notes |
|-------------|----------|----------|--------|--------|-------|
| `testEqualYears` | Compare same years | Equal | Equal | ✅ Passed | |
| `testCompareTo` | Compare different years | Correct ordering | Correct ordering | ✅ Passed | |
| `testFailCompareDifferentYearsExpectEqual` | Compare 2000 and 2020 | Should not be equal | Test expects equal | ⚠️ Invalid | Test logic error |
| `testFailCompareToNull` | Compare with null | NullPointerException | NullPointerException | ✅ Passed | |

### 📝 Formatting Tests
| Test Method | Scenario | Expected | Actual | Status | Notes |
|-------------|----------|----------|--------|--------|-------|
| `testToString` | Year string representation | "1999" | "1999" | ✅ Passed | |

### 🔍 Parsing Tests
| Test Method | Scenario | Expected | Actual | Status | Notes |
|-------------|----------|----------|--------|--------|-------|
| `testParseYear` | Parse valid year string | 2010 | 2010 | ✅ Passed | |
| `testInvalidParseYear` | Parse invalid string | TimePeriodFormatException | TimePeriodFormatException | ✅ Passed | |
| `testFailExpectWrongException` | Expect NumberFormatException | TimePeriodFormatException | TimePeriodFormatException | ❌ Failed | Wrong expected exception |

### ➡️ Navigation Tests
| Test Method | Scenario | Expected | Actual | Status | Notes |
|-------------|----------|----------|--------|--------|-------|
| `testPreviousYear` | Previous of 2020 | 2019 | 2019 | ✅ Passed | |
| `testNextYear` | Next of 2020 | 2021 | 2021 | ✅ Passed | |

### ❌ Failing Tests
| Test Method | Issue | Recommended Fix |
|-------------|-------|-----------------|
| `testFailExpectWrongException` | Wrong exception expected | Change to expect TimePeriodFormatException |
| `testFailEqualsWithDifferentYears` | Incorrect assertion | Change to assertFalse |
| `testFailEqualsWithWrongObjectType` | Incorrect assertion | Change to assertFalse |
| `testFailCompareDifferentYearsExpectEqual` | Invalid test logic | Should assertNotEquals |






