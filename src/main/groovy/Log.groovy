class Log {

    def steps
    String lastLog

    Log(steps) { this.steps = steps }

    static String debug(theSteps, message) {
        if (isDebug(theSteps)) {
            theSteps.echo "[DEBUG] ${message}"
            return "[DEBUG] ${message}"
        }
        return ""
    }

    def debug(message) {
        this.lastLog = debug(this.steps, message)
    }

    static boolean isDebug(steps) {
        return steps.env.DEBUG == 'true'
    }

    static String info(theSteps, message) {
        theSteps.echo "[INFO] ${message}"
        return "[INFO] ${message}"
    }

    def info(message) {
        this.lastLog = info(this.steps, message)
    }
}
