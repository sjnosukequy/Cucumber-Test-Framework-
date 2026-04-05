import * as fs from 'node:fs/promises';

const reportPath = './reports/allure';

try {
    await fs.rm(reportPath, { recursive: true, force: true });
    console.log(`Report deleted successfully ${reportPath}`);
} catch (err) {
    console.error('Error deleting report:', err);
}