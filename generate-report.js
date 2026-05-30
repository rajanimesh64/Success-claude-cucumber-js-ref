const reporter = require('cucumber-html-reporter');
const fs = require('fs');
const path = require('path');

const now = new Date();
const timestamp = `${now.getFullYear()}-${String(now.getMonth() + 1).padStart(2, '0')}-${String(now.getDate()).padStart(2, '0')}_${String(now.getHours()).padStart(2, '0')}-${String(now.getMinutes()).padStart(2, '0')}-${String(now.getSeconds()).padStart(2, '0')}`;
const reportFileName = `cucumber_report_${timestamp}.html`;
const reportPath = path.join('target', 'cucumber-html-reports', reportFileName);

// Merge rerun results into main cucumber.json — keeps latest result per scenario
const jsonPath = 'target/cucumber-reports/cucumber.json';
let merged = JSON.parse(fs.readFileSync(jsonPath, 'utf8'));

const rerunFiles = fs.readdirSync('target/cucumber-reports')
    .filter(f => /^rerun-\d+\.json$/.test(f))
    .sort((a, b) => parseInt(a.match(/\d+/)[0]) - parseInt(b.match(/\d+/)[0]));

for (const file of rerunFiles) {
    const rerunPath = `target/cucumber-reports/${file}`;
    const rerun = JSON.parse(fs.readFileSync(rerunPath, 'utf8'));
    if (!rerun.length) continue;

    const rerunMap = new Map();
    rerun.forEach(feature => {
        feature.elements.forEach(scenario => rerunMap.set(scenario.id, scenario));
    });

    merged = merged.map(feature => ({
        ...feature,
        elements: feature.elements.map(scenario =>
            rerunMap.has(scenario.id) ? rerunMap.get(scenario.id) : scenario
        )
    }));
}

fs.writeFileSync(jsonPath, JSON.stringify(merged, null, 2), 'utf8');

const options = {
    theme: 'hierarchy',
    jsonFile: jsonPath,
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
