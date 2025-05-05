*** Keywords ***
Open Image
    [Arguments]    ${image}
    ${sys}=    Evaluate    os.path.sep == '/'
    IF    ${sys}
        Start Process    gwenview    ${image}
        SikuliLibrary.Wait Until Screen Contain    ${image}    30
    END

*** Settings ***
Suite Teardown    Stop Remote Server
Library           SikuliLibrary
Library           Process

*** Test Cases ***
My Test
    Add Image Path    .
    Set Always Resize    0
    #Double Click    1.png    xOffset=0 yOffset=-100
    #Right Click    1.png
    Open Image    1.png
    Click    1.png
    #${coor} \ \ Create List \ 0 \ 0 \ 300 \ 300
    #Click Region    ${coor}
    #${text} \ \ Get Text
    #Log \ \ ${text}
