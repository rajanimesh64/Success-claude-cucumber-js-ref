const reporter = require('cucumber-html-reporter');
const fs = require('fs');
const path = require('path');

const now = new Date();
const timestamp = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}_${String(now.getHours()).padStart(2, '0')}-${String(now.getMinutes()).padStart(2, '0')}-${String(now.getSeconds()).padStart(2, '0')}`;
const reportFileName = `cucumber_report_${timestamp}.html`;
const reportPath = path.join('target', 'cucumber-html-reports', reportFileName);

const options = {
    theme: 'hierarchy',
    jsonFile: 'target/cucumber-reports/cucumber.json',
    output: reportPath,
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

// Copy local assets and replace CDN links in the generated report
const assetsDir = path.join('target', 'cucumber-html-reports', 'assets');
const fontsDir  = path.join('target', 'cucumber-html-reports', 'fonts');
fs.mkdirSync(assetsDir, { recursive: true });
fs.mkdirSync(fontsDir,  { recursive: true });

const assets = [
    'bootstrap.min.css',
    'jquery.min.js',
    'bootstrap.min.js',
    'moment.min.js'
];

assets.forEach(file => {
    fs.copyFileSync(
        path.join('report-assets', file),
        path.join(assetsDir, file)
    );
});

fs.readdirSync(path.join('report-assets', 'fonts')).forEach(file => {
    fs.copyFileSync(
        path.join('report-assets', 'fonts', file),
        path.join(fontsDir, file)
    );
});

let html = fs.readFileSync(reportPath, 'utf8');

html = html
    .replace(/https?:\/\/[^"]*bootstrap\.min\.css[^"]*/g, 'assets/bootstrap.min.css')
    .replace(/https?:\/\/[^"]*jquery[^"]*\.js[^"]*/g,     'assets/jquery.min.js')
    .replace(/https?:\/\/[^"]*bootstrap\.min\.js[^"]*/g,  'assets/bootstrap.min.js')
    .replace(/https?:\/\/[^"]*moment[^"]*\.js[^"]*/g,     'assets/moment.min.js');

fs.writeFileSync(reportPath, html, 'utf8');

console.log(`Report generated: ${reportPath}`);
