<!doctype html>
<html lang="en">
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
                                                                            <p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 16.8px;"><strong><span style="font-size:18px;">Your Enquiry has been matched by ${(order.quoteCompany.name)!"a company"}</span></strong><strong><span style="font-size:18px;">!</span></strong></p>
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
                                                                            <p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 16.8px;">This email is to confirm your order ${(order.uuid)!"#"} is successfully placed.</p>
                                                                        </div>
                                                                    </div>
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
                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-5" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
                            <tbody>
                                <tr>
                                    <td>
                                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #333; background-color: #1e232c; width: 620px; margin: 0 auto;" width="620">
                                            <tbody>
                                                <tr>
                                                    <td class="column column-1" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: left; font-weight: 400; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="66.66666666666667%">
                                                        <table border="0" cellpadding="0" cellspacing="0" class="text_block block-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
                                                            <tr>
                                                                <td class="pad" style="padding-bottom:10px;padding-left:20px;padding-right:20px;padding-top:10px;">
                                                                    <div style="font-family: sans-serif">
                                                                        <div class="" style="font-size: 12px; font-family: 'Lato', Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #FFFFFF; line-height: 1.2;">
                                                                            <p style="margin: 0; font-size: 14px; mso-line-height-alt: 16.8px;">PRODUCTS</p>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td class="column column-2" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: left; font-weight: 400; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="33.333333333333336%">
                                                        <table border="0" cellpadding="0" cellspacing="0" class="text_block block-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
                                                            <tr>
                                                                <td class="pad" style="padding-bottom:10px;padding-left:20px;padding-right:20px;padding-top:10px;">
                                                                    <div style="font-family: sans-serif">
                                                                        <div class="" style="font-size: 12px; font-family: 'Lato', Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #FFFFFF; line-height: 1.2;">
                                                                            <p style="margin: 0; font-size: 14px; mso-line-height-alt: 16.8px;"><span style="font-size:14px;">PRICE</span></p>
                                                                        </div>
                                                                    </div>
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
                        <#list items as item>
                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-6" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
                            <tbody>
                                <tr>
                                    <td>
                                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #333; background-color: #fff; width: 620px; margin: 0 auto;" width="620">
                                            <tbody>
                                                <tr>
                                                    <td class="column column-1" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: left; font-weight: 400; padding-top: 15px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="66.66666666666667%">
                                                        <table border="0" cellpadding="0" cellspacing="0" class="text_block block-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
                                                            <tr>
                                                                <td class="pad" style="padding-bottom:10px;padding-left:20px;padding-right:20px;padding-top:10px;">
                                                                    <div style="font-family: sans-serif">
                                                                        <div class="" style="font-size: 12px; font-family: 'Lato', Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #000000; line-height: 1.2;">
                                                                            <p style="margin: 0; font-size: 14px; mso-line-height-alt: 16.8px;"><span style="color:#000000;font-size:14px;"><a href="https://beefree.io" style="text-decoration:none;color:#000000;" target="_blank">${item.sku.title}</a></span></p>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td class="column column-2" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: left; font-weight: 400; padding-top: 15px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="33.333333333333336%">
                                                        <table border="0" cellpadding="0" cellspacing="0" class="text_block block-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
                                                            <tr>
                                                                <td class="pad" style="padding-bottom:10px;padding-left:20px;padding-right:20px;padding-top:10px;">
                                                                    <div style="font-family: sans-serif">
                                                                        <div class="" style="font-size: 12px; font-family: 'Lato', Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #000000; line-height: 1.2;">
                                                                            <p style="margin: 0; font-size: 14px; mso-line-height-alt: 16.8px;">$${item.price}</p>
                                                                        </div>
                                                                    </div>
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
                        </#list>

                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-8" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
                            <tbody>
                                <tr>
                                    <td>
                                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000; width: 620px; margin: 0 auto;" width="620">
                                            <tbody>
                                                <tr>
                                                    <td class="column column-1" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: left; font-weight: 400; padding-bottom: 5px; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="100%">
                                                        <table border="0" cellpadding="10" cellspacing="0" class="divider_block block-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
                                                            <tr>
                                                                <td class="pad">
                                                                    <div align="center" class="alignment">
                                                                        <table border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
                                                                            <tr>
                                                                                <td class="divider_inner" style="font-size: 1px; line-height: 1px; border-top: 1px dotted #CCCCCC;"><span> </span></td>
                                                                            </tr>
                                                                        </table>
                                                                    </div>
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
                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row row-9" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;" width="100%">
                            <tbody>
                                <tr>
                                    <td>
                                        <table align="center" border="0" cellpadding="0" cellspacing="0" class="row-content stack" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #333; background-color: #fff; width: 620px; margin: 0 auto;" width="620">
                                            <tbody>
                                                <tr>
                                                    <td class="column column-1" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: left; font-weight: 400; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="66.66666666666667%">
                                                        <table border="0" cellpadding="0" cellspacing="0" class="text_block block-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
                                                            <tr>
                                                                <td class="pad" style="padding-bottom:5px;padding-left:20px;padding-right:20px;padding-top:5px;">
                                                                    <div style="font-family: sans-serif">
                                                                        <div class="" style="font-size: 12px; font-family: 'Lato', Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #000000; line-height: 1.2;">
                                                                            <p style="margin: 0; font-size: 14px; mso-line-height-alt: 16.8px;"><strong>TOTAL</strong><br/></p>
                                                                        </div>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                    <td class="column column-2" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; text-align: left; font-weight: 400; padding-top: 5px; vertical-align: top; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;" width="33.333333333333336%">
                                                        <table border="0" cellpadding="0" cellspacing="0" class="text_block block-1" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;" width="100%">
                                                            <tr>
                                                                <td class="pad" style="padding-bottom:5px;padding-left:20px;padding-right:20px;padding-top:5px;">
                                                                    <div style="font-family: sans-serif">
                                                                        <div class="" style="font-size: 12px; font-family: 'Lato', Tahoma, Verdana, Segoe, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #000000; line-height: 1.2;">
                                                                            <p style="margin: 0; font-size: 14px; mso-line-height-alt: 16.8px;"><strong>$${order.totalValue}</strong></p>
                                                                        </div>
                                                                    </div>
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