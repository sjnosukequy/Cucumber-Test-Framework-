import * as fs from "node:fs/promises";
const logsPath = "./logs";

try {
  await fs.rm(logsPath, { recursive: true, force: true });
  console.log(`Logs deleted successfully ${logsPath}`);
} catch (err) {
  console.error("Error deleting logs:", err);
}
