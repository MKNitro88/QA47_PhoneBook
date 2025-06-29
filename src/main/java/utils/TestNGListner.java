package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.time.LocalDate;

public class TestNGListner implements ITestListener {
    Logger logger = LoggerFactory.getLogger(TestNGListner.class);

    @Override
    public void onFinish(ITestContext context) {
        ITestListener.super.onFinish(context);
        logger.info("test ended: " + context.getStartDate());
    }
    @Override
    public void onStart(ITestContext context) {
        ITestListener.super.onStart(context);
        logger.info("Start test: " + context.getStartDate());
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ITestListener.super.onTestFailedWithTimeout(result);
        logger.info("Test timeout: " + LocalDate.now());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
        logger.info("Test Failed But Within Success Percentage: " + LocalDate.now());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ITestListener.super.onTestSkipped(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ITestListener.super.onTestFailure(result);
        logger.info("test failed: "+result.getMethod() + " on "+ LocalDate.now());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ITestListener.super.onTestSuccess(result);
        logger.info("test success: " + result.getMethod() + " at: " + LocalDate.now());
    }

    @Override
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
        logger.info(result.getMethod() + " test started at: " + LocalDate.now());
    }
}
