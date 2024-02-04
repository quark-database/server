import subprocess

QUARK_SERVER_PATH = "build/Quark Server-jar-with-dependencies.jar"
DOCUMENTATION_PATH = "build/Documentation.md"
README_TEMPLATE_PATH = "Readme-Template.md"
README_PATH = "README.md"
MD_DOCS_TAG = "<!-- Quark:Docs -->"


def generate_documentation():
    quark_server = subprocess.Popen(["java", "--enable-preview", "-jar", QUARK_SERVER_PATH])
    time.sleep(5)
    quark_server.terminate()
    quark_server.wait()


def main():
    generate_documentation()

    with open(DOCUMENTATION_PATH, 'r') as documentation:
        documentation_content = documentation.read()

    with open(README_TEMPLATE_PATH, 'r') as readme_template:
        readme_template_content = readme_template.read()
        readme_template_content = readme_template_content.replace(
            MD_DOCS_TAG, documentation_content)

    with open(README_PATH, 'w') as readme:
        readme.write(readme_template_content)


if __name__ == '__main__':
    main()
