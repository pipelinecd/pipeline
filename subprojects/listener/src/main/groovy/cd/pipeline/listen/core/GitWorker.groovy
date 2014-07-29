package cd.pipeline.listen.core

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import com.yammer.dropwizard.lifecycle.Managed
import groovy.util.logging.Slf4j

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Slf4j
class GitWorker implements Managed {

    private final EventBus bus

    GitWorker(EventBus bus) {
        this.bus = bus
    }

    @Override
    void start() throws Exception {
        bus.register(this)
    }

    @Override
    void stop() throws Exception {
        bus.unregister(this)
    }

    @Subscribe
    void work(GitTriggerEvent event) {
        log.info('Received event [{}]', event)
        def dir = Files.createTempDirectory("pipeline")
        log.debug('Created pipeline directory [{}]', dir)

        def workDirName = "work"
        def workDir = Paths.get(dir.toString(), workDirName)
        List envProps = null

        def gitCloneCommand = "git clone ${event.url} ${workDirName}"
        executeCommand(gitCloneCommand, dir, envProps)

        def gitCheckoutCommand = "git checkout --force ${event.branch}"
        executeCommand(gitCheckoutCommand, workDir, envProps)

        def runnerCommand = "pipe-runner run project.pipe"
        executeCommand(runnerCommand, workDir, envProps)
    }

    private void executeCommand(String command, Path workDir, List envProps) {
        log.info('Executing command [{}] in pipeline directory [{}]', command, workDir)
        def runnerProc = command.execute(envProps, workDir.toFile())
        def runnerExitStatus = runnerProc.waitFor()
        log.info('Command [{}] exited with status [{}]', command, runnerExitStatus)
    }
}
