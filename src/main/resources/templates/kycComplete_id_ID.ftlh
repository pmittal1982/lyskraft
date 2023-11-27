<!doctype html>
<html lang="id">
    <head>
        <link href="https://metaltradev2.s3.ap-southeast-1.amazonaws.com/resources/email.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet" type="text/css">
    </head>
    <body style="text-size-adjust: none; background-color: #fff; margin: 0; padding: 0;">
        <table border="0" cellpadding="0" cellspacing="0" class="nl-container" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #fff;" width="100%">
            <tbody>
                <tr>
                    <td>
                        <#include "header.ftlh">
                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-4" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
                            <tbody>
                                <tr>
                                    <td>
                                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000; width: 620px; margin: 0 auto;" width="620">
                                            <tbody>
                                                <tr>
                                                    <td class="column column-1" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: left; font-weight: 400; padding-bottom: 10px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="100%">
                                                        <table border="0" cellpadding="0" cellspacing="0" class="text_block block-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
                                                            <tr>
                                                                <td class="pad" style="padding-bottom:5px;padding-left:10px;padding-right:10px;padding-top:10px;">
                                                                    <div style="font-family: sans-serif">
                                                                        <div class="" style="font-size: 12px; font-family: 'Lato', Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #000000; line-height: 1.2;">
                                                                            <p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 16.8px;"><strong><span style="font-size:18px;">Your KYC is successfully completed</span></strong><strong><span style="font-size:18px;">!</span></strong></p>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table border="0" cellpadding="0" cellspacing="0" class="text_block block-2" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
                                                            <tr>
                                                                <td class="pad" style="padding-bottom:25px;padding-left:10px;padding-right:10px;padding-top:10px;">
                                                                    <div style="font-family: sans-serif">
                                                                        <div class="" style="font-size: 12px; font-family: 'Lato', Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #71777D; line-height: 1.2;">
                                                                            <p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 16.8px;">This email is to confirm ${(user.company.name)!"company"} KYC is complete.</p>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                        <table border="0" cellpadding="5" cellspacing="0" class="button_block block-2" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
                                                            <tr>
                                                                <td class="pad">
                                                                    <div align="center" class="alignment"><!--[if mso]><v:roundrect xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w="urn:schemas-microsoft-com:office:word" href="https://metaltrade.io" style="height:42px;width:121px;v-text-anchor:middle;" arcsize="10%" stroke="false" fillcolor="#6750a4"><w:anchorlock/><v:textbox inset="0px,0px,0px,0px"><center style="color:#ffffff; font-family:Tahoma, Verdana, sans-serif; font-size:16px"><![endif]--><a href="metaltrade.io" style="text-decoration:none;display:inline-block;color:#ffffff;background-color:#6750a4;border-radius:4px;width:auto;border-top:0px solid transparent;font-weight:400;border-right:0px solid transparent;border-bottom:0px solid transparent;border-left:0px solid transparent;padding-top:5px;padding-bottom:5px;font-family:'Lato', Tahoma, Verdana, Segoe, sans-serif;font-size:16px;text-align:center;mso-border-alt:none;word-break:keep-all;" target="_blank"><span style="padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;letter-spacing:normal;"><span style="word-break: break-word; line-height: 32px;">Start Trading</span></span></a><!--[if mso]></center></v:textbox></v:roundrect><![endif]--></div>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <#include "footer.ftlh">
                    </td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
