# GPG Keyboxd I/O Error Fix

## Problem

When using GPG 2.5.18 (or newer versions), you may encounter this error during Maven deployment:

```
gpg: sending fd 0x000000000000023c to keyboxd: Input/output error <GnuPG>
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-gpg-plugin:3.1.0:sign (sign-artifacts) on project simple-captcha: Exit code: 2
```

This is a known issue with GPG's keyboxd daemon in newer versions. Even though the signature is successfully created, the keyboxd error causes GPG to return exit code 2, which makes Maven fail.

## Solution

### Method 1: Kill keyboxd Process (Recommended)

Before running `mvn clean deploy`, stop the keyboxd daemon:

```bash
# Windows (PowerShell)
C:\path\to\GnuPG\bin\gpgconf.exe --kill keyboxd

# Linux/Mac
gpgconf --kill keyboxd
```

Then run your Maven deploy command:

```bash
mvn clean deploy
```

### Method 2: Create GPG Configuration Files

Create or edit the following files in your GPG home directory (`~/.gnupg` or `C:\Users\YourName\AppData\Roaming\gnupg` on Windows):

**gpg.conf:**
```
use-agent
no-tty
```

**gpg-agent.conf:**
```
disable-scdaemon
```

Then restart GPG agent:

```bash
# Windows
C:\path\to\GnuPG\bin\gpgconf.exe --kill gpg-agent

# Linux/Mac
gpgconf --kill gpg-agent
```

### Method 3: Use Older GPG Version

If the above methods don't work, consider downgrading to GPG 2.4.x or 2.2.x, which don't have this keyboxd issue.

## Verification

After applying the fix, verify that GPG signing works:

```bash
# Check GPG version
gpg --version

# List your keys
gpg --list-keys

# Test signing (should not produce keyboxd errors)
echo "test" | gpg --batch --yes --pinentry-mode loopback --passphrase "your-passphrase" --sign
```

## Maven GPG Plugin Configuration

Ensure your `pom.xml` has the correct GPG plugin configuration:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-gpg-plugin</artifactId>
    <version>3.1.0</version>
    <configuration>
        <executable>C:\path\to\GnuPG\bin\gpg.exe</executable> <!-- or just 'gpg' if in PATH -->
        <gpgArguments>
            <arg>--pinentry-mode</arg>
            <arg>loopback</arg>
            <arg>--batch</arg>
            <arg>--yes</arg>
            <arg>--no-tty</arg>
        </gpgArguments>
    </configuration>
    <executions>
        <execution>
            <id>sign-artifacts</id>
            <phase>verify</phase>
            <goals>
                <goal>sign</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## References

- [GPG Documentation](https://gnupg.org/documentation/)
- [Maven GPG Plugin](https://maven.apache.org/plugins/maven-gpg-plugin/)
- [Sonatype Central Portal](https://central.sonatype.com/)

## Date

This solution was tested and verified on 2026-07-22 with:
- GPG version: 2.5.18
- Maven GPG Plugin: 3.1.0
- Java: OpenJDK 23
- OS: Windows 11