--
--
--
CREATE OR REPLACE TYPE AvgInterval 
AS OBJECT (

    runningSum INTERVAL DAY(9) TO SECOND(9),
    runningCount NUMBER,
    
    STATIC FUNCTION ODCIAggregateInitialize(
        actx IN OUT AvgInterval
    ) RETURN NUMBER,
    
    MEMBER FUNCTION ODCIAggregateIterate(
        self  IN OUT AvgInterval,
        val   IN     DSINTERVAL_UNCONSTRAINED
    ) RETURN NUMBER,
    
    MEMBER FUNCTION ODCIAggregateTerminate(
        self         IN  AvgInterval,
        returnValue  OUT DSINTERVAL_UNCONSTRAINED,
        flags        IN  NUMBER
    ) RETURN NUMBER,
    
    MEMBER FUNCTION ODCIAggregateMerge(
        self  IN OUT AvgInterval,
        ctx2  IN     AvgInterval
    ) RETURN NUMBER

);
/

--
--
--
CREATE OR REPLACE TYPE BODY AvgInterval AS
    --
    --
    --
    STATIC FUNCTION ODCIAggregateInitialize(
        actx IN OUT AvgInterval
    ) RETURN NUMBER IS 
    BEGIN
        IF actx IS NULL THEN
          --dbms_output.put_line('NULL INIT');
          actx := AvgInterval(INTERVAL '0 0:0:0.0' DAY TO SECOND, 0);
        ELSE
          --dbms_output.put_line('NON-NULL INIT');
          actx.runningSum := INTERVAL '0 0:0:0.0' DAY TO SECOND;
          actx.runningCount := 0;
        END IF;
        RETURN ODCIConst.Success;
    END;
    --
    --
    --
    MEMBER FUNCTION ODCIAggregateIterate(
        self  IN OUT AvgInterval,
        val   IN     DSINTERVAL_UNCONSTRAINED
    ) RETURN NUMBER IS
    BEGIN
        --DBMS_OUTPUT.PUT_LINE('Iterate ' || TO_CHAR(val));
        /*
        IF val IS NULL THEN 
            -- Will never happen
            DBMS_OUTPUT.PUT_LINE('Null on iterate');
        END IF;
        */
        self.runningSum := self.runningSum + val;
     --   self.runningCount := self.runningCount + 1;
        RETURN ODCIConst.Success;
    END;
    --
    --
    --
    MEMBER FUNCTION ODCIAggregateTerminate(
        self        IN  AvgInterval,
        ReturnValue OUT DSINTERVAL_UNCONSTRAINED,
        flags       IN  NUMBER
    ) RETURN NUMBER IS
    BEGIN
        dbms_output.put_line('Terminate ' || to_char(flags) || to_char(self.runningSum));
        IF self.runningCount <> 0 THEN
          returnValue := self.runningSum / self.runningCount;
        ELSE
          -- It *is* possible to have an empty group, so avoid divide-by-zero.
          returnValue := self.runningSum;
        END IF;
        RETURN ODCIConst.Success;
    END;
    --
    --
    --
    MEMBER FUNCTION ODCIAggregateMerge(
        self IN OUT AvgInterval,
        ctx2 IN     AvgInterval
    ) RETURN NUMBER IS
    BEGIN
        self.runningSum := self.runningSum + ctx2.runningSum;
        self.runningCount := self.runningCount + ctx2.runningCount;
        RETURN ODCIConst.Success;
    END;    
END;
/

--
--
--
CREATE OR REPLACE FUNCTION avg_interval(
    x DSINTERVAL_UNCONSTRAINED
) RETURN DSINTERVAL_UNCONSTRAINED
PARALLEL_ENABLE
AGGREGATE USING AvgInterval;
/
