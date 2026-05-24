const reporter = require('cucumber-html-reporter');

const options = {
    theme: 'hierarchy',
    jsonFile: 'target/cucumber-reports/cucumber.json',
    output: 'target/cucumber-html-reports/cucumber_report.html',
    reportSuiteAsScenarios: true,
    scenarioTimestamp: true,
    launchReport: false,
    metadata: {
        'App Version': '1.0.0',
        'Test Environment': 'LOCAL',
        'Browser': 'Chrome - Latest',
        'Platform': 'Windows 11',
        'Parallel': 'Scenarios',
        'Executed': 'Local'
    }
};

reporter.generate(options);
